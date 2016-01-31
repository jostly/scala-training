package se.citerus.scala.calculator

import scala.io.StdIn.readLine
import scala.math.BigDecimal.RoundingMode
import scala.util.{Failure, Success, Try}

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

  private def calculate(accumulatedGross: BigDecimal, purchaseAmount: BigDecimal): (BigDecimal, BigDecimal) = {
    val amountToPay = new Calculator(accumulatedGross = accumulatedGross).amountToPay(purchaseAmount)

    ((accumulatedGross + purchaseAmount).setScale(2, RoundingMode.HALF_UP), amountToPay.setScale(2, RoundingMode.HALF_UP))
  }

  def run(prompt: String => String, display: String => Unit): Unit = {
    def readValue(p: String): BigDecimal = {
      val v = BigDecimal(prompt(p))
      if (v < 0) throw new IllegalArgumentException("Amount must be 0 or greater")
      v
    }

    val input = Try(readValue("Accumulated purchase value: "))
      .flatMap(av => Try((av, readValue("Purchase amount: "))))

    input.map(i => calculate(i._1, i._2)) match {
      case Success((newAccumulatedGross, amountToPay)) =>
        display(s"Amount to pay: $amountToPay")
        display(s"New accumulated purchase value: $newAccumulatedGross")
      case Failure(e: NumberFormatException) =>
        display("Please input a decimal number")
      case Failure(e: IllegalArgumentException) =>
        display(e.getMessage)
      case Failure(e) =>
        display(e.toString)
    }
  }

  def main(args: Array[String]): Unit = {
    run(s => readLine(s), s => println(s))
  }

}
