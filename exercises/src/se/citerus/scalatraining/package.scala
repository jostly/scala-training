package se.citerus

package object scalatraining {

  def timed[R](msg: String = "Elapsed time")(block: => R): R = {
    val t0 = System.nanoTime()
    var i = 0
    while (i < 99) {
      val result = block    // call-by-name
      i += 1
    }
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println(s"$msg: ${(t1 - t0)/(1e6*i)} ms")
    result
  }

  def functional_vs_imperative_map_and_sum(): Unit = {
    val things = Range(0, 1000).toList

    def imperative_map_sum(l: List[Int]): Int = {
      var out = 0
      var work = l
      while(work.nonEmpty) {
        val t = work.head
        out += t * 17
        work = work.tail
      }
      out
    }

    timed("Functional") { things.map(_*17).sum }
    timed("Iterative ") { imperative_map_sum(things) }
  }


}



