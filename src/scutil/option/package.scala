package scutil

package object option {
  def None[T] = Option[T]()

  implicit class RichValue[T](val v: T) extends AnyVal {
    def some = Some(v)
  }
}
