package scutil.react.test

import scutil.react._

object main {
  def main(args: Array[String]) {
    val v1 = obsRef("v1", 1)
    val v2 = obsRef("v2", 2)

    val v3 = for {
      a <- v1
      b <- v2
    } yield a + b
    
    println("v3 = " + v3())
//    v1.subscribe(v => println("v1 = " + v))
//    v2.subscribe(v => println("v2 = " + v))
    v3.subscribe(v => println("v3 = " + v))
    println("v1(5)")
    v1(5)
    println("v1(8)")
    v1(8)
    println("v1(10)")
    v1(10)
    println("v2(7)")
    v2(7)
    println("cnt = " + v1.observerCount)
    println("cnt = " + v2.observerCount)
    println("v3 = " + v3())
  }
}
