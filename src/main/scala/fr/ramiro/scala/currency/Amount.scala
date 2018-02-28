package fr.ramiro.scala.currency

import fr.ramiro.scala.currency.Currency._
import java.text.NumberFormat

import scala.language.implicitConversions
import scala.runtime.ScalaRunTime

case class Amount(value: AmountType, currency: Currency)(implicit currencyConversion: CurrencySettings) extends Ordered[Amount] {
  def to(targetCurrency: Currency): Amount = if (targetCurrency == currency) {
    this
  } else {
    Amount(value * currencyConversion.converter(currency, targetCurrency), targetCurrency)
  }

  def +(amount: Amount): Amount = performOperation(amount) { _ + _ }

  def -(amount: Amount): Amount = performOperation(amount) { _ - _ }

  def *(amount: Amount): Amount = performOperation(amount) { _ * _ }

  def /(amount: Amount): Amount = performOperation(amount) { _ / _ }

  def unary_- : Amount = copy(value = -value)

  private def performOperation(amount: Amount)(operation: (AmountType, AmountType) => AmountType) = {
    Amount(operation(value, (amount to currency).value), currency)
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Amount if this.canEqual(other) =>
      this.value == other.to(this.currency).value
    case _ => false
  }

  override def hashCode(): Int = scala.util.hashing.MurmurHash3.productHash(this.to(currencyConversion.base))

  override def canEqual(other: Any): Boolean = other.isInstanceOf[Amount]

  override def compare(that: Amount): Int = value compare (that to currency).value

  /*
  override def toString: String = {
    val formatter = NumberFormat.getCurrencyInstance(currencyConversion.locale)
    formatter.setCurrency(currency.toJava)
    formatter.format(value)
  }
  */
}