package se.citerus.scalatraining.collections

import org.scalatest.{Matchers, FunSuite}

class CalculatorTest extends FunSuite with Matchers {

  def calculator = Calculator

  test("no discount below limit") {
    calculator.calculateAmountToPay(0, 100) should be (100)
    calculator.calculateAmountToPay(50, 50) should be (50)
    calculator.calculateAmountToPay(99, 1) should be (1)
  }

  test("discount when purchasing over limit") {
    calculator.calculateAmountToPay(100, 100) should be (90)
    calculator.calculateAmountToPay(150, 100) should be (90)
    calculator.calculateAmountToPay(100, 1) should be (0.9)
  }

  test("partial discount when crossing the limit") {
    calculator.calculateAmountToPay(0, 200) should be (100 + 90)
    calculator.calculateAmountToPay(50, 100) should be (50 + 45)
    calculator.calculateAmountToPay(99, 2) should be (1 + 0.9)
  }


}
