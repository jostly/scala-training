package se.citerus.scalatraining.collections

import java.time.LocalDate

class Account(val purchases: List[Purchase]) {

  def purchase(purchaseDate: LocalDate, purchaseValue: BigDecimal): Account = {
    val accumulated = accumulatedValue(purchaseDate)
    val amountToPay = Calculator.calculateAmountToPay(accumulated, purchaseValue)

    val purchase = Purchase(
      date = purchaseDate,
      value = purchaseValue,
      accumulatedValue = accumulated,
      amountPaid = amountToPay
    )

    new Account(purchase :: purchases)
  }

  def lastPaidAmount: BigDecimal = ???

  def accumulatedValue(date: LocalDate): BigDecimal = ???

  override def toString: String =
    s"Account(currentAccumulatedValue = ${accumulatedValue(LocalDate.now())}, lastPaidAmount: $lastPaidAmount)"

  override def equals(obj: scala.Any): Boolean = obj match {
    case a: Account => purchases == a.purchases
    case _ => false
  }

  override def hashCode(): Int = purchases.hashCode()
}

object Account {
  def apply(): Account = new Account(List.empty)
}

