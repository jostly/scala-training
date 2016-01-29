package se.citerus.scala.tracker

import java.time.LocalDate

case class MemberRecord(accumulatedValue: BigDecimal, dateOfFirstPurchase: LocalDate) {
  def increasedBy(amount: BigDecimal): MemberRecord = copy(accumulatedValue = accumulatedValue + amount)
}
