import scala.util.{Failure, Success, Try}

def someFunction(): Int = ???

Try(someFunction()) match {
  case Success(v) => println(s"Got return value: $v")
  case Failure(t) => println(s"Something went wrong: $t")
}
