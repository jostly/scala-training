package se.citerus.scalatraining.collections

import java.time.LocalDate

import scala.util.Try

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

  def lastPaidAmount: BigDecimal = Try(purchases.head.amountPaid).getOrElse(0)

  def accumulatedValue(date: LocalDate): BigDecimal = {
    val oneYearAgo = date.minusYears(1)
    val firstPurchaseOfLoyaltyPeriod = purchases.find(_.accumulatedValue == 0)

    firstPurchaseOfLoyaltyPeriod match {
      case Some(Purchase(d, _, _, _)) if d.isAfter(oneYearAgo) =>
        purchases.headOption.map(p => p.accumulatedValue + p.value).getOrElse(0)
      case _ =>
        0
    }
  }

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

