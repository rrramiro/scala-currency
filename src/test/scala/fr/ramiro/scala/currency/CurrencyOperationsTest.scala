package fr.ramiro.scala.currency

import org.scalatest.FunSuite
import scala.language.implicitConversions
import java.util.{ Currency => JavaCurrency }
import fr.ramiro.scala.currency.Currency._

class CurrencyOperationsTest extends FunSuite {
  private implicit val currencyConversion = CurrencyConversionConfiguration(USD, GBP -> 0.8)

  test("Addition") {
    val dollars = USD(2.0)
    val pounds = GBP(2.0)
    val total = dollars + pounds + 2.0
    assert(total === USD(6.5))
  }

  test("Check all native currency") {
    values.foreach { currency =>
      assert(currency.native !== null)
    }
  }

  test("use java currency") {
    val usd = JavaCurrency.getInstance("USD")
    val pounds = GBP(2.0)
    val dollars = pounds to usd
    assert(dollars === USD(2.5))
  }

  test("sum") {
    val list = Seq(USD(2.0), GBP(2.0))
    assert(list.sum === USD(4.5))
  }

  test("display") {
    assert(GBP(2.0).toString === "£2.00")
    assert(USD(2.0).toString === "USD2.00")
    assert(EUR(2.0).toString === "€2.00")
    assert(ITL(2.0).toString === "ITL2.00")
  }
}
