package se.citerus.scalatraining.exercise_4

import java.time.LocalDate

case class Purchase(date: LocalDate, value: BigDecimal, accumulatedValue: BigDecimal, amountPaid: BigDecimal)
