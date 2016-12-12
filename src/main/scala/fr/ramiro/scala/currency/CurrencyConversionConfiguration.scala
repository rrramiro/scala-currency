package fr.ramiro.scala.currency

import java.util.Locale

class CurrencyConversionConfiguration(val locale: Locale, val base: Currency, val converter: ConverterMethodType)

object CurrencyConversionConfiguration {
  def apply(base: Currency, rates: (Currency, AmountType)*): ConfigurationType = {
    new CurrencyConversionConfiguration(
      locale = Locale.getDefault,
      base = base,
      converter = rates.flatMap {
        case (currency, rate) => Seq(
          (base -> currency) -> rate,
          (currency -> base) -> 1.0 / rate
        )
      }.toMap
    )
  }
}
