package se.citerus.scalatraining.futures

import java.time.LocalDate

import org.scalatest.concurrent.{IntegrationPatience, Eventually}
import org.scalatest.{Matchers, FunSuite}

import scala.concurrent.Await
import scala.util.Success

import scala.concurrent.duration._

class ConsumerTest extends FunSuite with Matchers with Eventually with IntegrationPatience {

  test("increasing the accumulated value of an account") {
    val consumer = new Consumer()

    // Given an account with id 12345 and accumulated value 10
    consumer.storage.save(Account("12345", LocalDate.now(), 10))

    val result = consumer.increaseAccumulatedValue("12345", 20)

    eventually {
      result.value should be (Some(Success(BigDecimal(30))))
    }
  }

  test("allaccounts") {
    val consumer = new Consumer()

    val accounts = consumer.allAccounts()

    val t0 = System.currentTimeMillis()

    println(Await.result(accounts, 5.second))

    val t1 = System.currentTimeMillis()

    println(s"It took ${t1-t0} ms")

  }

}
