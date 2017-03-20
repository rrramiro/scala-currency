package fr.ramiro.scala.currency.rest

import org.scalatest.FunSuite
import fr.ramiro.scala.currency.{ CurrencySettings, _ }
import fr.ramiro.scala.currency.Currency._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class FixerTest extends FunSuite with FixerFixture {

  test("Get rates from an fixer") {
    val expected = CurrencySettings(base = USD, GBP -> 0.78977)
    val actual = parseFixerResponse("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}""")
    assert(actual === expected)
  }

  test("remote test") {
    implicit val converterContext = Await.result(ratesFromFixer, Duration.Inf)
    println(USD(2.0) + GBP(2.0) + 2.0)
  }
}
