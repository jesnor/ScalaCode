package scutil.react.test

import scutil.react._

object main {
  def main(args: Array[String]) {
    val v1 = obsRef(1)
    val v2 = obsRef(2)

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
    println("v2(7)")
    v2(7)
    println("v3 = " + v3())
  }
}
