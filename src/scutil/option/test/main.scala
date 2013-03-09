package scutil.option.test

import scutil.option._

object main {
  def calc(o1: Option[Int], o2: Option[Int]): Option[Int] =
    for {
      a <- o1
      if a > 5
      b <- o2
    } yield a + b
    
  def usedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
  
  case object Obj
  case class Test1(o : Option[Obj.type])
  case class Test2(o : scala.Option[Obj.type])
  
  def main(args: Array[String]) {
    val o1 = 1.some
    val o4: Option[Any] = o1
    val o2 = 4.some
    val o3 = calc(o1, o2)
    println(o3)
    println(o1 == o1)
    println(o1 == o2)
    println(o1 == Some(1))
    println(o1 == o4)

    o1 match {
      case Some(x) => println(x)
      case ONone() => println("Nothing")
    }
    
    val m1 = usedMem
    val a1 = 0 until 1000000 map (i => Test1(Option(Obj)))
    val m2 = usedMem
    val a2 = 0 until 1000000 map (i => Test2(scala.Some(Obj)))
    val m3 = usedMem
    println((m2 - m1) + ", " + (m3 - m2))
  }

}