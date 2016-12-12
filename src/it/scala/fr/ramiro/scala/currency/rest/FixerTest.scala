package fr.ramiro.scala.currency.rest

import java.net.URL
import java.util.{Currency => JavaCurrency}
import com.stackmob.newman._
import com.stackmob.newman.dsl._
import net.liftweb.json.JsonAST.{JDouble, JField, JObject}
import net.liftweb.json.{DefaultFormats, JsonParser}
import org.scalatest.FunSuite
import scala.concurrent._
import scala.concurrent.duration._
import fr.ramiro.scala.currency._

class FixerTest extends FunSuite{
  test("Get rates from an fixer"){
    println(fromJsonFixer("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}"""))
  }

  def getRatesFromFixer: String = {
    implicit val httpClient = new ApacheHttpClient
    Await.result(GET(new URL("http://api.fixer.io/latest?base=USD&symbols=USD,GBP")).apply, 1.second).bodyString
  }

  def fromJsonFixer(jsonString: String): ConfigurationType = {
    val json = JsonParser.parse(jsonString)
    implicit val formats = DefaultFormats
    val base = JavaCurrency.getInstance((json \ "base").extract[String])
    val rates = for( JObject(child) <-  json \ "rates" ; JField(currencyName, JDouble(rate)) <- child ) yield {
      (JavaCurrency.getInstance(currencyName): Currency, rate: AmountType)
    }
    CurrencyConversionConfiguration(base, rates:_ *)
  }
}
