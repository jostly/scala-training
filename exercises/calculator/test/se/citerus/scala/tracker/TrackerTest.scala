package se.citerus.scala.tracker

import java.time.LocalDate

import org.scalatest.FunSuite

class TrackerTest extends FunSuite {

  test("calculate purchase for new customer") {
    val tracker = new Tracker(limit = BigDecimal(100), discountPercentage = BigDecimal(10))
    val id = MemberId("1")

    val now = LocalDate.now()
    val (amount, accumulatedValue, dateOfFirstPurchase) =
      tracker.purchase(id, BigDecimal(150), now)

    assert(amount === BigDecimal(145))
    assert(accumulatedValue === BigDecimal(150))
    assert(dateOfFirstPurchase === now)
  }

  test("accumulates purchases for customer") {
    val tracker = new Tracker(limit = BigDecimal(100), discountPercentage = BigDecimal(10))
    val id = MemberId("1")

    val now = LocalDate.now()
    val before = now.minusDays(3)

    assert(tracker.purchase(id, BigDecimal(50), before) === (BigDecimal(50), BigDecimal(50), before))

    val (amount, accumulatedValue, dateOfFirstPurchase) =
      tracker.purchase(id, BigDecimal(60), now)

    assert(amount === BigDecimal(59))
    assert(accumulatedValue === BigDecimal(110))
    assert(dateOfFirstPurchase === before)
  }

  test("purchases are valid up to one year") {
    val tracker = new Tracker(limit = BigDecimal(100), discountPercentage = BigDecimal(10))
    val id = MemberId("1")

    val now = LocalDate.now()
    val before = now.minusYears(1)

    tracker.purchase(id, 100, before)

    assert(tracker.purchase(id, 50, now) === (45, 150, before))
  }

  test("purchases expire after one year") {
    val tracker = new Tracker(limit = BigDecimal(100), discountPercentage = BigDecimal(10))
    val id = MemberId("1")

    val now = LocalDate.now()
    val before = now.minusDays(1).minusYears(1)

    tracker.purchase(id, 100, before)

    assert(tracker.purchase(id, 50, now) === (50, 50, now))
  }

  test("tracks purchases for different customers") {
    val tracker = new Tracker(limit = BigDecimal(100), discountPercentage = BigDecimal(10))

    val id1 = MemberId("1")
    val id2 = MemberId("2")

    val now = LocalDate.now()
    val before = now.minusDays(3)

    tracker.purchase(id1, 100, before)

    assert(tracker.purchase(id1, 50, now) === (45, 150, before))
    assert(tracker.purchase(id2, 50, now) === (50, 50, now))
  }

}
