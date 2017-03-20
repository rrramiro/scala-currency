package fr.ramiro.scala.currency.rest

import org.scalatest.FunSuite
import com.fasterxml.jackson.core.JsonParser
import com.typesafe.config.ConfigFactory
import fr.ramiro.scala.currency._
import fr.ramiro.scala.currency.Currency._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json._
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.collection.Map
import scala.util._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

class FixerTest extends FunSuite{
  implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }
  implicit val materializer = ActorMaterializer()

  // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
  val wsClient = StandaloneAhcWSClient()

  case class FixerResponse(base: Currency, date: String, rates: Map[Currency, AmountType])

  def enumReads[E <: Enumeration](enum: E): Reads[E#Value] = Reads {
    case JsString(s) => Try(JsSuccess(enum.withName(s)))
      .getOrElse(JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$s'"))
    case _ => JsError("String value expected")
  }

  implicit val currencyReads: Reads[Currency.Value] = enumReads(Currency)
  implicit def ratesReads(implicit amountTypeReads: Reads[AmountType]): Reads[Map[Currency, AmountType]] = Reads{
    case JsObject(map) =>
      Try(JsSuccess(map.map{case (key, value) => Currency.withName(key) -> amountTypeReads.reads(value).get })).getOrElse(JsError("error"))
    case _ => throw new Exception("error")
  }
  implicit val fixerResponseReads: Reads[FixerResponse] = Json.reads[FixerResponse]

  test("Get rates from an fixer"){
    val expected = FixerResponse(base = USD, date = "2016-12-08", rates = Map(GBP -> (0.78977: AmountType)))
    val actual = parseFixerResponse("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}""")
    assert(actual === expected)
  }

  test("remote test"){
    ratesFromFixer.foreach { implicit converterContext =>
      println(USD(2.0) + GBP(2.0) + 2.0)
    }
  }

  def parseFixerResponse(json: String): FixerResponse = {
    Json.fromJson(Json.parse("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}"""))(fixerResponseReads) match {
      case JsSuccess(v, _) => v
      case JsError(e) => throw new Exception(s"Fail to parse Fixer response: $e")
    }
  }

  def ratesFromFixer(implicit ec: ExecutionContext): Future[CurrencySettings] = {
    wsClient.url("http://api.fixer.io/latest?base=USD&symbols=USD,GBP").get().map { response =>
      val fixerResponse = parseFixerResponse(response.body)
      CurrencySettings(base = fixerResponse.base, rates = fixerResponse.rates.toSeq: _*)
    }.andThen { case _ => wsClient.close() }.andThen { case _ => system.terminate() }
  }
}
