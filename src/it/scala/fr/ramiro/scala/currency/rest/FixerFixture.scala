package fr.ramiro.scala.currency.rest

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import fr.ramiro.scala.currency.{ AmountType, Currency, CurrencySettings }
import org.scalatest.{ BeforeAndAfterAll, Suite }
import play.api.libs.json._
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try

trait FixerFixture extends BeforeAndAfterAll { this: Suite =>
  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()
  private val wsClient = StandaloneAhcWSClient()

  override def afterAll: Unit = {
    Try(wsClient.close())
    Try(system.terminate())
  }

  implicit val currencyReads: Reads[Currency] = Reads {
    case JsString(s) => Try(JsSuccess(Currency.withName(s)))
      .getOrElse(JsError(s"Enumeration expected of type: '${Currency.getClass}', but it does not appear to contain the value: '$s'"))
    case _ => JsError("String value expected")
  }

  implicit val ratesReads: Reads[Seq[(Currency, AmountType)]] = Reads {
    case jsObj @ JsObject(objMap) =>
      Try {
        JsSuccess(objMap.map {
          case (key, _) =>
            Currency.withName(key) -> (jsObj \ key).as[AmountType]
        }.toSeq)
      }.getOrElse(JsError("error"))
    case _ => throw new Exception("error")
  }

  implicit val fixerResponseReads: Reads[CurrencySettings] = Reads {
    case jsObj @ JsObject(_) =>
      Try {
        JsSuccess(
          CurrencySettings(
            base = (jsObj \ "base").as[Currency],
            rates = (jsObj \ "rates").as[Seq[(Currency, AmountType)]]: _*
          )
        )
      }.getOrElse(JsError("error"))
    case _ => throw new Exception("error")
  }

  def parseFixerResponse(json: String): CurrencySettings = {
    Json.fromJson[CurrencySettings](Json.parse("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}""")).getOrElse {
      throw new Exception(s"Fail to parse Fixer response")
    }
  }

  def ratesFromFixer(implicit ec: ExecutionContext): Future[CurrencySettings] = {
    wsClient.url("http://api.fixer.io/latest?base=USD&symbols=USD,GBP").get().map { response =>
      parseFixerResponse(response.body)
    }
  }
}
