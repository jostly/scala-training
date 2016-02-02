package se.citerus.scalatraining.exercise_4

object Calculator {

  private val limit = BigDecimal(100)
  private val discountMultiplier = BigDecimal(0.9)

  def calculateAmountToPay(accumulatedValue: BigDecimal,
                           purchaseValue: BigDecimal): BigDecimal = {

    if (accumulatedValue >= limit) {
      purchaseValue * discountMultiplier
    } else if (accumulatedValue + purchaseValue > limit) {
      val fullPricePart = limit - accumulatedValue
      fullPricePart + (purchaseValue - fullPricePart) * discountMultiplier
    } else {
      purchaseValue
    }
  }

}
