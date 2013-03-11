package scutil.option

case class Option[+T](value: T = null.asInstanceOf[T]) extends AnyVal with OptionBase {
  def isEmpty = value == null
  def filter(f: T => Boolean): Option[T] = if (isEmpty || !f(value)) None else this
  def withFilter(f: T => Boolean): Option[T] = filter(f)
  def map[U: NotNothing](f: T => U): Option[U] = if (isEmpty) None else Option(f(value))
  def flatMap[U: NotNothing](f: T => Option[U]): Option[U] = if (isEmpty) None else f(value)
  def fold[U: NotNothing](f1: T => U, f2: () => U): U = if (isEmpty) f2() else f1(value)
  def toScalaOption: scala.Option[T] = if (isEmpty) scala.None else scala.Some(value)
  override def toString = if (isEmpty) "None" else "Some(" + value + ")"
}
