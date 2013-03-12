package scutil.option.test

import scutil.option._
import language.postfixOps

object main {
  def calc(o1: Option[Int], o2: Option[Int]): Option[Int] =
    for {
      a <- o1
      if a > 5
      b <- o2
    } yield a + b

  def usedMem = {
    System.gc
    System.gc
    System.gc
    Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
  }

  def report(t: String, a: => Any): Any = {
    val m1 = usedMem
    val v = a
    val m2 = usedMem
    println(t + ": " + (m2 - m1) / 1024 / 1024 + " MB")
    v
  }

  def foo = None

  def foo2 = {
    val oo1 = foo
    val i: Option[Int] = oo1
    println(i)
  }

  case object Obj
  case class Test1(o: Option[Obj.type])
  case class Test2(o: scala.Option[Obj.type])
  case class Test3(a: Any)

  def main(args: Array[String]) {
    foo2
    val o1 = 10.some
    val oo1 = None
    val o4: Option[Any] = o1
    val o2 = 4.some
    val o3 = calc(o1, o2)
    println(o3)
    println(o1 == o1)
    println(o1 == o2)
    println(o1 == Some(1))
    println(o1 == o4)

    o3 match {
      case Some(x) => println("Some(" + x + ")")
      case None    => println("Nothing")
    }

    val range = 0 until 1000000
    report("scala.Some", range map (i => Test2(scala.Some(Obj))))
    report("scala.None", range map (i => Test2(scala.None)))
    report("scala.Some : Any", range map (i => Test3(scala.Some(Obj))))
    report("scala.None : Any", range map (i => Test3(scala.None)))
    report("AnyVal Some", range map (i => Test1(Obj.some)))
    report("AnyVal None", range map (i => Test1(None)))
    report("AnyVal Some : Any", range map (i => Test3(Obj.some)))
    report("AnyVal None : Any", range map (i => Test3(None)))
  }
}
