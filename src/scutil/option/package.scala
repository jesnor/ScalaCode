package scutil

import language.implicitConversions

package object option {
  implicit class RichValue[T](val v: T) {
    def some = Some(v)
  }

  implicit def noneToOption[T](n: None.type) = Option[T]()

  trait NotNothing[T]
  implicit def notNothing[T]: NotNothing[T] = null
  implicit def notNothing1[T <: Nothing]: NotNothing[T] = null
  implicit def notNothing2[T <: Nothing]: NotNothing[T] = null
}
