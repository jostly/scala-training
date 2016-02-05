package se.citerus.practicalscala.exercise1

case class Calculator(accumulatedValue: BigDecimal) {
  val discountLimit: BigDecimal = 100
  val discountPercentage: BigDecimal = 10

  def amountAfterDiscount(amount: BigDecimal): BigDecimal = ???

}
