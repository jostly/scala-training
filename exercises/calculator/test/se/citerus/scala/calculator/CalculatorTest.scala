package se.citerus.scala.calculator

import org.scalatest.FunSuite

import scala.collection.mutable

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

  test("run calculator") {
    val io = new IoStub(
      "Accumulated purchase value: " -> "50",
      "Purchase amount: " -> "100"
    )

    Calculator.run(io.prompt, io.display)

    assert(io.displays === List(
      "Amount to pay: 95",
      "New accumulated purchase value: 150"
    ))
  }

  test("inputting something other than a decimal number for accumulated value gives error") {
    val io = new IoStub("Accumulated purchase value: " -> "arghdsd")

    Calculator.run(io.prompt, io.display)

    assert(io.displays === List("Please input a decimal number"))
  }

  test("inputting something other than a decimal number for purchase value gives error") {
    val io = new IoStub(
      "Accumulated purchase value: " -> "0",
      "Purchase amount: " -> "hnjkd"
    )

    Calculator.run(io.prompt, io.display)

    assert(io.displays === List("Please input a decimal number"))
  }

  test("inputting a negative number for accumulated value gives error") {
    val io = new IoStub("Accumulated purchase value: " -> "-1")

    Calculator.run(io.prompt, io.display)

    assert(io.displays === List("Amount must be 0 or greater"))
  }
}

class IoStub(expectedPrompts: (String, String)*) {
  private val _prompts = mutable.Map(expectedPrompts: _*)
  private val _displays = mutable.ListBuffer.empty[String]

  def prompt(p: String): String = {
    _prompts.remove(p).getOrElse(throw new RuntimeException(s"Unexpected prompt $p"))
  }

  def display(s: String): Unit = {
    _displays += s
  }

  def displays: List[String] = _displays.toList
}
