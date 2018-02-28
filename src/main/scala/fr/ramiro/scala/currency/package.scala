package fr.ramiro.scala

import scala.language.implicitConversions

package object currency {
  type JavaCurrency = java.util.Currency
  type Currency = Currency.Value
  type AmountType = BigDecimal
  type ConverterFunction = Function[(Currency, Currency), AmountType]

  object JavaCurrency {
    def apply(currencyCode: String): JavaCurrency = java.util.Currency.getInstance(currencyCode)
  }

}
