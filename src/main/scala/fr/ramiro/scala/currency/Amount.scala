package fr.ramiro.scala.currency

case class Amount(value: AmountType, currency: Currency) extends Ordered[Amount] {
  //TODO currency conversion
  def to(targetCurrency: Currency): Amount = this

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

  //TODO hashCode
  override def hashCode(): Int = this.hashCode()

  override def canEqual(other: Any): Boolean = other.isInstanceOf[Amount]

  override def compare(that: Amount): Int = value compare (that to currency).value

  //TODO toString
  override def toString: String = this.toString
}