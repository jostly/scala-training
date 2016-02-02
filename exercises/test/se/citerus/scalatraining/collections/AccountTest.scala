package se.citerus.scalatraining.collections

import java.time.LocalDate
import java.time.LocalDate.now

import org.scalatest.{FunSuite, Matchers}

class AccountTest extends FunSuite with Matchers {

  test("an empty account has 0 last paid amount") {
    val account = Account()

    account.lastPaidAmount should be (0)
    account.purchases should be (Nil)
  }

  test("an account after one purchase has correct last paid amount and accumulated value, and list of purchases") {
    val today = now()

    val account = Account().purchase(today, 50)

    account.lastPaidAmount should be (50)
    account.accumulatedValue(today) should be (50)
    account.purchases should be (List(Purchase(today, 50, 0, 50)))
  }

  test("an account after two purchases has correct last paid amount and accumulated value, and list of purchases") {
    val today = now()

    val account = Account()
      .purchase(today, 100)
      .purchase(today, 50)

    account.lastPaidAmount should be (45)
    account.accumulatedValue(today) should be (150)
    account.purchases should be (List(
      Purchase(today, 50, 100, 45),
      Purchase(today, 100, 0, 100)))
  }

  test("purchases within one year are accumulated") {
    val today = now()
    val firstPurchase = today.minusYears(1).plusDays(1)

    val account = Account()
      .purchase(firstPurchase, 100)
      .purchase(now(), 50)

    account.lastPaidAmount should be (45)
    account.accumulatedValue(now()) should be (150)
  }

  test("purchases over a year ago are not included in calculation") {
    val today = now()
    val firstPurchase = today.minusYears(1)

    val account = Account()
      .purchase(firstPurchase, 100)
      .purchase(now(), 50)

    account.lastPaidAmount should be (50)
    account.accumulatedValue(now()) should be (50)
  }

  test("purchases within the last loyalty period are not included in calculation") {
    val today = now()
    val yesterday = today.minusDays(1) // Last day of the previous loyalty period
    val firstPurchase = today.minusYears(1)

    val account = Account()
      .purchase(firstPurchase, 100)
      .purchase(yesterday, 20)
      .purchase(now(), 50)

    account.lastPaidAmount should be (50)
    account.accumulatedValue(now()) should be (50)
  }

}
