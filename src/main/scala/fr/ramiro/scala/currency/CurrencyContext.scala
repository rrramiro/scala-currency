package fr.ramiro.scala.currency

import java.util.Locale

case class CurrencyContext(converter: ConverterFunction, base: Currency, locale: Locale)

object CurrencyContext {
  def apply(base: Currency, rates: (Currency, AmountType)*): CurrencyContext = {
    CurrencyContext(
      converter = rates.flatMap {
        case (currency, rate) => Seq(
          (base -> currency) -> rate,
          (currency -> base) -> 1.0 / rate
        )
      }.toMap,
      base = base,
      locale = Locale.getDefault
    )
  }
}
