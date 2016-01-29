package se.citerus.scala.tracker

import java.time.LocalDate

import se.citerus.scala.calculator.Calculator

class Tracker(limit: BigDecimal = BigDecimal(100),
              discountPercentage: BigDecimal = BigDecimal(10)) {

  var storage = Map.empty[MemberId, MemberRecord]

  def purchase(id: MemberId, amount: BigDecimal, date: LocalDate): (BigDecimal, BigDecimal, LocalDate) = {

    var record = storage.get(id) match {
      case None => MemberRecord(0, date)
      case Some(MemberRecord(_, d)) if d.isBefore(date.minusYears(1)) => MemberRecord(0, date)
      case Some(r) => r
    }

    val calculator = new Calculator(record.accumulatedValue, limit, discountPercentage)

    record = record.increasedBy(amount)

    storage += (id -> record)

    (calculator.amountToPay(amount), record.accumulatedValue, record.dateOfFirstPurchase)
  }

}
