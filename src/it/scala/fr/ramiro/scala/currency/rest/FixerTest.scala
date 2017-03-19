package fr.ramiro.scala.currency.rest

import java.net.URL

import com.stackmob.newman._
import com.stackmob.newman.dsl._

import net.liftweb.json.JsonAST._
import net.liftweb.json.{DefaultFormats, JsonParser}
import org.scalatest.FunSuite
import scala.concurrent._
import fr.ramiro.scala.currency._
import fr.ramiro.scala.currency.Currency._
import scala.concurrent.ExecutionContext.Implicits.global

class FixerTest extends FunSuite{
  test("Get rates from an fixer"){
    println(fromJsonFixer("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}"""))
  }

  test("remote test"){
    ratesFromFixer.foreach { implicit converterContext =>
      println(USD(2.0) + GBP(2.0) + 2.0)
    }
  }

  def ratesFromFixer(implicit ec: ExecutionContext): Future[CurrencyContext] = {
    implicit val httpClient = new ApacheHttpClient
    GET(new URL("http://api.fixer.io/latest?base=USD&symbols=USD,GBP")).apply.map {
      remoteRates =>
        fromJsonFixer(remoteRates.bodyString)
    }
  }

  def fromJsonFixer(jsonString: String): CurrencyContext = {
    val json = JsonParser.parse(jsonString)
    implicit val formats = DefaultFormats

    CurrencyContext(
      base = Currency.withName((json \ "base").extract[String]),
      rates = (
        for( JObject(child) <-  json \ "rates" ; JField(currencyName, JDouble(rate)) <- child )
          yield Currency.withName(currencyName) -> (rate: AmountType)
        ): _*
    )
  }
}
