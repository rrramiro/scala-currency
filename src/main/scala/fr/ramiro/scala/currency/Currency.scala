package fr.ramiro.scala.currency

import java.util.{ Currency => JavaCurrency }
import scala.language.implicitConversions

object Currency extends Enumeration {
  val MXN, TRL, QAR, BYR, EEK, KES, SDD, CHW, XFO, AOA, LAK, BGL, SDG, MOP, PAB, LYD, UGX, NLG, UYI, GTQ, BTN, PGK, SVC, CZK, SGD, GHC, ITL, ROL, CVE, SOS, SRD, MMK, JOD, IQD, RUR, XAG, SYP, ZWN, LRD, THB, XTS, BAM, DJF, LUF, XCD, BIF, FIM, LKR, AUD, DOP, MVR, LBP, USD, BSD, RWF, SAR, AYM, MUR, RUB, XAF, JPY, XPF, ERN, SKK, XBC, COU, MZM, ADP, KMF, MDL, PKR, FRF, BWP, LSL, SLL, ZMW, IRR, KRW, AWG, MXV, BRL, HNL, XAU, ALL, SBD, INR, KHR, MKD, XBB, AED, VEF, TTD, ETB, KGS, BMD, XBA, CAD, GMD, SZL, YUM, SEK, CSD, HTG, DZD, PEN, KWD, HKD, GEL, AZM, SCR, CRC, WST, USS, CUP, GHS, AMD, IDR, MGA, FJD, CHE, EUR, MWK, ZWD, OMR, STD, BZD, FKP, LVL, NGN, VEB, TOP, XOF, SSP, TWD, BGN, IEP, MYR, XPD, XXX, BBD, DKK, BOV, CUC, USN, NZD, JMD, PLN, TND, BHD, NAD, TJS, BEF, UZS, RSD, BOB, LTL, CLP, BDT, VND, BYB, HUF, ZWR, GYD, XSU, NOK, AFN, MZN, XFU, CLF, SHP, ISK, TMT, ZWL, ANG, ARS, RON, XPT, TRY, AZN, UYU, MAD, PHP, GIP, TZS, MTL, NIO, MRO, KZT, CYP, YER, XDR, ILS, KYD, MNT, ZAR, PTE, TMM, XBD, UAH, GWP, GRD, GBP, ESP, NPR, TPE, XUA, SIT, GNF, COP, HRK, CHF, CDF, AFA, PYG, SRG, BND, DEM, VUV, MGF, CNY, ZMK, EGP, ATS, KPW = Value

  implicit def javaCurrencyToCurrency(javaCurrency: JavaCurrency): Currency.Value = {
    this.values.find(_.native.getCurrencyCode == javaCurrency.getCurrencyCode).getOrElse(throw new IllegalArgumentException(s"${javaCurrency.getCurrencyCode} is not a valid currency code"))
  }

  implicit class CurrencyWrapper(delegate: Currency.Value) {
    def native: JavaCurrency = JavaCurrency.getInstance(delegate.toString)

    def apply[A <: AnyVal](value: A)(implicit currencyConversion: ConfigurationType, toNumber: A => Number): Amount = {
      Amount(value.doubleValue(), delegate)
    }
  }
}
