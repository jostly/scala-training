package se.citerus.scala.calculator

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class Calculator(accumulatedGross: BigDecimal,
                 limit: BigDecimal = BigDecimal(100),
                 discountPercentage: BigDecimal = BigDecimal(10)) {

  require(accumulatedGross >= 0, "accumulated gross must be 0 or greater")

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

  def calculate(accumulatedGross: BigDecimal, purchaseAmount: BigDecimal): (BigDecimal, BigDecimal) = {
    val amountToPay = new Calculator(accumulatedGross = accumulatedGross).amountToPay(purchaseAmount)

    (accumulatedGross + purchaseAmount, amountToPay)
  }


  def main(args: Array[String]): Unit = {

    val input = readInput

    input.map(i => calculate(i._1, i._2)) match {
      case Success((newAccumulatedGross, amountToPay)) =>
        println(s"Amount to pay: $amountToPay")
        println(s"New accumulated gross: $newAccumulatedGross")
      case Failure(e: NumberFormatException) =>
        println("Input a valid decimal number")
      case Failure(e) =>
        println("Illegal input: " + e.getMessage)
    }

  }

  def readInput: Try[(BigDecimal, BigDecimal)] = {
    Try((
      BigDecimal(readLine("Accumulated gross purchases: ")),
      BigDecimal(readLine("Purchase amount: "))
      ))
  }
}
