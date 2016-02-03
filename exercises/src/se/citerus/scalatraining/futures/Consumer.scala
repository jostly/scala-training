package se.citerus.scalatraining.futures

import java.time.LocalDate

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


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
  def allAccounts(): Future[Set[Option[Account]]] = {
    val allIds = ids
    allIds.flatMap { (setOfIds: Set[String]) =>
      val futureAccounts: List[Future[Option[Account]]] = setOfIds.map(id => findById(id)).toList

      val extracted: Future[List[Option[Account]]] = extractFutures(futureAccounts, Nil)

      extracted.map(list => list.toSet)
    }
  }

  /*
  A functional mindset:
  Extracing all the values of a list of futures is the same as
  - extracting the value of the first future and
  - appending the extracted values of the rest of the list of futures

  Implement this as a recursive function, which uses an acc parameter to collect
  the list of extracted futures
   */

  def extractFutures[T](futures: List[Future[T]], acc: List[T]): Future[List[T]] = {
    futures match {
      case x :: xs =>
        x.flatMap(a => extractFutures(xs, a :: acc))
      case Nil =>
        Future(acc)
    }
  }

  // Implement in exercise 8
  def activeAccounts(today: LocalDate): Future[Set[Account]] = ???

  // Async wrappers for storage access
  def findById(id: String): Future[Option[Account]] = Future { storage.findById(id) }

  def save(account: Account): Future[Account] = Future { storage.save(account) }

  def ids: Future[Set[String]] = Future { storage.ids }

}
