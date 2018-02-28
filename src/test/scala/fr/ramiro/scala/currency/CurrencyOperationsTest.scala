package fr.ramiro.scala.currency

import org.scalatest.FunSuite
import fr.ramiro.scala.currency.Currency._
import scala.language.implicitConversions

class CurrencyOperationsTest extends FunSuite {
  private implicit val currencySettings = CurrencySettings(base = GBP, USD -> 2.0)
/*
  test("implicit conversion from double to amount of money with default currency") {
    val pounds = GBP(2.0)
    val amountWithDefaultCurrency = 2.0: Amount
    assert(pounds === amountWithDefaultCurrency)
  }
  */
/*
  test("Conversion from one currency to another") {
    val dollars = USD(2.0)
    val pounds = dollars to GBP
    assert(pounds === GBP(1.0))
  }
  */
/*
  test("test equality") {
    assert(USD(2.0) === USD(2.0))
    assert(USD(2.0) === GBP(1.0))
  }
  */
/*

  test("Addition") {
    val dollars = USD(2.0)
    val pounds = GBP(2.0)
    val total = dollars + pounds + 2.0
    assert(total === USD(10))
  }
*/

/*
  test("sum") {
    val list = Seq(USD(2.0), GBP(2.0))
    assert(list.sum === GBP(3.0))
  }
*/
/*
  test("Check all native currency") {
    values.foreach { currency =>
      assert(currency.toJava !== null)
    }
  }
  */
/*
  test("use java currency") {
    val usd = JavaCurrency("USD").toScala
    val pounds = GBP(2.0)
    val dollars = pounds to usd
    assert(dollars === USD(4.0))
  }
*/

/*  test("display") {
    assert(GBP(2.0).toString === "£2.00")
    assert(USD(2.0).toString === "USD2.00")
    assert(EUR(2.0).toString === "€2.00")
    assert(ITL(2.0).toString === "ITL2.00")
  }*/
}
