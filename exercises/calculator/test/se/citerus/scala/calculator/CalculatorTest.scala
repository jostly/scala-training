package se.citerus.scala.calculator

import org.scalatest.FunSuite

class CalculatorTest extends FunSuite {

  test("no discount below limit") {
    val calculator = new Calculator(accumulatedGross = BigDecimal(0),
      limit = BigDecimal(100),
      discountPercentage = BigDecimal(10))

    val amountToPay: BigDecimal = calculator.amountToPay(BigDecimal(100))

    assert(amountToPay === BigDecimal(100))
  }

  test("discount when purchasing over limit") {
    val calculator = new Calculator(
      accumulatedGross = BigDecimal(100),
      limit = BigDecimal(100),
      discountPercentage = BigDecimal(10))

    val amountToPay: BigDecimal = calculator.amountToPay(BigDecimal(100))

    assert(amountToPay === BigDecimal(90))
  }

  test("partial discount when crossing the limit") {
    val calculator = new Calculator(
      accumulatedGross = BigDecimal(50),
      limit = BigDecimal(100),
      discountPercentage = BigDecimal(10))

    val amountToPay: BigDecimal = calculator.amountToPay(BigDecimal(100))

    assert(amountToPay === BigDecimal(95))
  }

}
