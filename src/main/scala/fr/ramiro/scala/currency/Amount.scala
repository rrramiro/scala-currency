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

object Amount {
  implicit def amountWithDefaultCurrency(value: Double)(implicit currencySettings: CurrencySettings): Amount = {
    Amount(value, currencySettings.base)
  }

  implicit def numericAmount(implicit currencySettings: CurrencySettings): Numeric[Amount] = new Numeric[Amount] {
    override def plus(x: Amount, y: Amount): Amount = x + y

    override def minus(x: Amount, y: Amount): Amount = x - y

    override def times(x: Amount, y: Amount): Amount = x * y

    override def negate(x: Amount): Amount = -x

    override def fromInt(x: Int): Amount = Amount(x.toDouble, currencySettings.base)

    override def toInt(x: Amount): Int = x.toInt()

    override def toLong(x: Amount): Long = x.toLong()

    override def toFloat(x: Amount): Float = x.toFloat()

    override def toDouble(x: Amount): Double = x.toDouble()

    override def compare(x: Amount, y: Amount): Int = x.compare(y)
  }
}