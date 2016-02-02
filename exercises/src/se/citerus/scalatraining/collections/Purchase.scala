package se.citerus.scalatraining.collections

import java.time.LocalDate

case class Purchase(date: LocalDate, value: BigDecimal, accumulatedValue: BigDecimal, amountPaid: BigDecimal)
