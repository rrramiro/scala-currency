package fr.ramiro.scala.currency

import java.util.{ Currency => JavaCurrency }
import scala.collection.immutable.Seq
import scala.language.implicitConversions

object Currency {
  case object MXN extends Currency
  case object TRL extends Currency
  case object QAR extends Currency
  case object BYR extends Currency
  case object EEK extends Currency
  case object KES extends Currency
  case object SDD extends Currency
  case object CHW extends Currency
  case object XFO extends Currency
  case object AOA extends Currency
  case object LAK extends Currency
  case object BGL extends Currency
  case object SDG extends Currency
  case object MOP extends Currency
  case object PAB extends Currency
  case object LYD extends Currency
  case object UGX extends Currency
  case object NLG extends Currency
  case object UYI extends Currency
  case object GTQ extends Currency
  case object BTN extends Currency
  case object PGK extends Currency
  case object SVC extends Currency
  case object CZK extends Currency
  case object SGD extends Currency
  case object GHC extends Currency
  case object ITL extends Currency
  case object ROL extends Currency
  case object CVE extends Currency
  case object SOS extends Currency
  case object SRD extends Currency
  case object MMK extends Currency
  case object JOD extends Currency
  case object IQD extends Currency
  case object RUR extends Currency
  case object XAG extends Currency
  case object SYP extends Currency
  case object ZWN extends Currency
  case object LRD extends Currency
  case object THB extends Currency
  case object XTS extends Currency
  case object BAM extends Currency
  case object DJF extends Currency
  case object LUF extends Currency
  case object XCD extends Currency
  case object BIF extends Currency
  case object FIM extends Currency
  case object LKR extends Currency
  case object AUD extends Currency
  case object DOP extends Currency
  case object MVR extends Currency
  case object LBP extends Currency
  case object USD extends Currency
  case object BSD extends Currency
  case object RWF extends Currency
  case object SAR extends Currency
  case object AYM extends Currency
  case object MUR extends Currency
  case object RUB extends Currency
  case object XAF extends Currency
  case object JPY extends Currency
  case object XPF extends Currency
  case object ERN extends Currency
  case object SKK extends Currency
  case object XBC extends Currency
  case object COU extends Currency
  case object MZM extends Currency
  case object ADP extends Currency
  case object KMF extends Currency
  case object MDL extends Currency
  case object PKR extends Currency
  case object FRF extends Currency
  case object BWP extends Currency
  case object LSL extends Currency
  case object SLL extends Currency
  case object ZMW extends Currency
  case object IRR extends Currency
  case object KRW extends Currency
  case object AWG extends Currency
  case object MXV extends Currency
  case object BRL extends Currency
  case object HNL extends Currency
  case object XAU extends Currency
  case object ALL extends Currency
  case object SBD extends Currency
  case object INR extends Currency
  case object KHR extends Currency
  case object MKD extends Currency
  case object XBB extends Currency
  case object AED extends Currency
  case object VEF extends Currency
  case object TTD extends Currency
  case object ETB extends Currency
  case object KGS extends Currency
  case object BMD extends Currency
  case object XBA extends Currency
  case object CAD extends Currency
  case object GMD extends Currency
  case object SZL extends Currency
  case object YUM extends Currency
  case object SEK extends Currency
  case object CSD extends Currency
  case object HTG extends Currency
  case object DZD extends Currency
  case object PEN extends Currency
  case object KWD extends Currency
  case object HKD extends Currency
  case object GEL extends Currency
  case object AZM extends Currency
  case object SCR extends Currency
  case object CRC extends Currency
  case object WST extends Currency
  case object USS extends Currency
  case object CUP extends Currency
  case object GHS extends Currency
  case object AMD extends Currency
  case object IDR extends Currency
  case object MGA extends Currency
  case object FJD extends Currency
  case object CHE extends Currency
  case object EUR extends Currency
  case object MWK extends Currency
  case object ZWD extends Currency
  case object OMR extends Currency
  case object STD extends Currency
  case object BZD extends Currency
  case object FKP extends Currency
  case object LVL extends Currency
  case object NGN extends Currency
  case object VEB extends Currency
  case object TOP extends Currency
  case object XOF extends Currency
  case object SSP extends Currency
  case object TWD extends Currency
  case object BGN extends Currency
  case object IEP extends Currency
  case object MYR extends Currency
  case object XPD extends Currency
  case object XXX extends Currency
  case object BBD extends Currency
  case object DKK extends Currency
  case object BOV extends Currency
  case object CUC extends Currency
  case object USN extends Currency
  case object NZD extends Currency
  case object JMD extends Currency
  case object PLN extends Currency
  case object TND extends Currency
  case object BHD extends Currency
  case object NAD extends Currency
  case object TJS extends Currency
  case object BEF extends Currency
  case object UZS extends Currency
  case object RSD extends Currency
  case object BOB extends Currency
  case object LTL extends Currency
  case object CLP extends Currency
  case object BDT extends Currency
  case object VND extends Currency
  case object BYB extends Currency
  case object HUF extends Currency
  case object ZWR extends Currency
  case object GYD extends Currency
  case object XSU extends Currency
  case object NOK extends Currency
  case object AFN extends Currency
  case object MZN extends Currency
  case object XFU extends Currency
  case object CLF extends Currency
  case object SHP extends Currency
  case object ISK extends Currency
  case object TMT extends Currency
  case object ZWL extends Currency
  case object ANG extends Currency
  case object ARS extends Currency
  case object RON extends Currency
  case object XPT extends Currency
  case object TRY extends Currency
  case object AZN extends Currency
  case object UYU extends Currency
  case object MAD extends Currency
  case object PHP extends Currency
  case object GIP extends Currency
  case object TZS extends Currency
  case object MTL extends Currency
  case object NIO extends Currency
  case object MRO extends Currency
  case object KZT extends Currency
  case object CYP extends Currency
  case object YER extends Currency
  case object XDR extends Currency
  case object ILS extends Currency
  case object KYD extends Currency
  case object MNT extends Currency
  case object ZAR extends Currency
  case object PTE extends Currency
  case object TMM extends Currency
  case object XBD extends Currency
  case object UAH extends Currency
  case object GWP extends Currency
  case object GRD extends Currency
  case object GBP extends Currency
  case object ESP extends Currency
  case object NPR extends Currency
  case object TPE extends Currency
  case object XUA extends Currency
  case object SIT extends Currency
  case object GNF extends Currency
  case object COP extends Currency
  case object HRK extends Currency
  case object CHF extends Currency
  case object CDF extends Currency
  case object AFA extends Currency
  case object PYG extends Currency
  case object SRG extends Currency
  case object BND extends Currency
  case object DEM extends Currency
  case object VUV extends Currency
  case object MGF extends Currency
  case object CNY extends Currency
  case object ZMK extends Currency
  case object EGP extends Currency
  case object ATS extends Currency
  case object KPW extends Currency

  lazy val values: Seq[Currency] = {
    import scala.reflect.runtime.{ universe => ru }
    val m = ru.runtimeMirror(getClass.getClassLoader)
    ru.typeOf[Currency].typeSymbol.asClass.knownDirectSubclasses.toList.map { sym =>
      m.reflectModule(sym.asInstanceOf[scala.reflect.internal.Symbols#Symbol].sourceModule.asInstanceOf[ru.Symbol].asModule)
        .instance.asInstanceOf[Currency]
    }
  }

  implicit def javaCurrencyToCurrency(javaCurrency: JavaCurrency): Currency = {
    this.values.find(_.native.getCurrencyCode == javaCurrency.getCurrencyCode).getOrElse(throw new IllegalArgumentException(s"${javaCurrency.getCurrencyCode} is not a valid currency code"))
  }
}

sealed trait Currency {
  lazy val native: JavaCurrency = JavaCurrency.getInstance(this.toString)

  def apply[A <: AnyVal](value: A)(implicit currencyConversion: ConfigurationType, toNumber: A => Number): Amount = Amount(value.doubleValue(), this)
}