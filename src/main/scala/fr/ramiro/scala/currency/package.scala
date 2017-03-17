package fr.ramiro.scala

import scala.language.implicitConversions

package object currency {
  type Currency = Currency.Value
  type AmountType = BigDecimal
  type ConverterMethodType = PartialFunction[(Currency, Currency), AmountType]
  type ConfigurationType = CurrencyConversionConfiguration
}
