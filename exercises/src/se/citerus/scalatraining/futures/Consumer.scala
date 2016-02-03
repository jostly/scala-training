package se.citerus.scalatraining.futures

import java.time.LocalDate

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Consumer(val storage: Storage = Storage()) {

  def accumulatedValue(id: String): Future[BigDecimal] = Future {
    val account = storage.findById(id)
    account match {
      case Some(a) => a.accumulatedValue
      case None => 0
    }
  }

  // Implement in exercise 5
  def increaseAccumulatedValue(id: String, amount: BigDecimal): Future[BigDecimal] = {

    val t: Future[Account] = findById(id).map {
      case Some(account) => account.copy(accumulatedValue = account.accumulatedValue + amount)
      case None => Account(id, LocalDate.now(), amount)
    }

    val s: Future[Account] = t.flatMap { updatedAccount => save(updatedAccount) }

    s.map { acct => acct.accumulatedValue }
  }


  // Implement in exercise 7
  def allAccounts(): Future[Set[Account]] = ???

  // Implement in exercise 8
  def activeAccounts(today: LocalDate): Future[Set[Account]] = ???

  // Async wrappers for storage access
  def findById(id: String): Future[Option[Account]] = Future { storage.findById(id) }

  def save(account: Account): Future[Account] = Future { storage.save(account) }

  def ids: Future[Set[String]] = Future { storage.ids }

}
