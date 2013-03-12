package scutil

import language.implicitConversions

package object option {
  val None = Option[Nothing]()
  
  implicit class RichValue[T](val v: T) extends AnyVal {
    def some = Some(v)
  }

  implicit class RichOption[T](val o: scala.Option[T]) extends AnyVal {
    def toAnyValOption: Option[T] = if (o.isEmpty) None else o.get.some
  }

  trait NotNothing[T]
  implicit def notNothing[T]: NotNothing[T] = null
  implicit def notNothing1[T <: Nothing]: NotNothing[T] = null
  implicit def notNothing2[T <: Nothing]: NotNothing[T] = null
}
