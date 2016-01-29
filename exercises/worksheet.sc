import java.util.Date

case class MemberRecord(firstPurchase: Date, accumulatedValue: BigDecimal)

val record: Option[MemberRecord] = ??? // look up record in storage

record match {
  case None => ???
  case Some(MemberRecord(date, value)) if date.before(oneYearAgo()) => ???
  case Some(MemberRecord(date, value)) => ???
}
