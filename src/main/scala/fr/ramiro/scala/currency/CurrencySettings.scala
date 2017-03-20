package fr.ramiro.scala.currency

import java.util.Locale

case class CurrencySettings(converter: ConverterFunction, base: Currency, fractionDigits: Int, locale: Locale)

object CurrencySettings {
  def apply(base: Currency, rates: (Currency, AmountType)*): CurrencySettings = {
    CurrencySettings(
      converter = rates.flatMap {
        case (currency, rate) => Seq(
          (base -> currency) -> rate,
          (currency -> base) -> 1.0 / rate
        )
      }.toMap,
      base = base,
      2, //TODO Get fractionDigits from java Currency
      locale = Locale.getDefault
    )
  }
}
