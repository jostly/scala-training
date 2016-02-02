package se.citerus.scalatraining.futures

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

  def increaseAccumulatedValue(id: String, amount: BigDecimal): Future[BigDecimal] = ???

  // In exercise 7
  // def allAccounts(): Future[Set[Account]] = ???

  // Async wrappers for storage access
  def findById(id: String): Future[Option[Account]] = Future { storage.findById(id) }

  def save(account: Account): Future[Account] = Future { storage.save(account) }

  def ids: Future[Set[String]] = Future { storage.ids }

}
