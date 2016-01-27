package se.citerus.scala.calculator

import scala.io.StdIn.readLine

class Calculator(accumulatedGross: BigDecimal,
                 limit: BigDecimal = BigDecimal(100),
                 discountPercentage: BigDecimal = BigDecimal(10)) {

  def amountToPay(purchaseAmount: BigDecimal): BigDecimal =
    if (accumulatedGross >= limit) {
      discountOf(purchaseAmount)
    } else if (accumulatedGross + purchaseAmount > limit) {
      val nonDiscountedPart = limit - accumulatedGross
      nonDiscountedPart + discountOf(purchaseAmount - nonDiscountedPart)
    } else {
      purchaseAmount
    }

  private def discountOf(amount: BigDecimal): BigDecimal =
    amount * (BigDecimal(100) - discountPercentage) / BigDecimal(100)

}

object Calculator {
  def main(args: Array[String]): Unit = {
    var accumulatedGross = BigDecimal(readLine("Accumulated gross purchases: "))
    val purchaseAmount = BigDecimal(readLine("Purchase amount: "))

    val amountToPay = new Calculator(accumulatedGross = accumulatedGross).amountToPay(purchaseAmount)

    accumulatedGross += purchaseAmount

    println(s"Amount to pay: $amountToPay")
    println(s"New accumulated gross: $accumulatedGross")
  }
}
