package fr.ramiro.scala.currency

import java.util.Locale
import fr.ramiro.scala.currency.Currency._

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
      base.toJava.getDefaultFractionDigits,
      locale = Locale.getDefault
    )
  }
}
