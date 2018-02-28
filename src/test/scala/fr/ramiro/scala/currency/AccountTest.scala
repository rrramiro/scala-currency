package fr.ramiro.scala.currency

import org.scalatest.FunSuite
import fr.ramiro.scala.currency.Currency._

class AccountTest extends FunSuite {
  private implicit val currencySettings = CurrencySettings(base = GBP, USD -> 2.0)

  test("operations"){
    val left = Amount(2.0, GBP)
    val right = Amount(2.0, GBP)
    assert (left + right === Amount(4.0, GBP))
    assert (left - right === Amount(0.0, GBP))
    assert (left / right === Amount(1.0, GBP))
    assert (left * right === Amount(4.0, GBP))
    assert (-left === Amount(-2.0, GBP))
  }

  test("conversion to currency"){
    assert(Amount(2.0, GBP).to(USD) === Amount(4.0, USD))
  }

}
