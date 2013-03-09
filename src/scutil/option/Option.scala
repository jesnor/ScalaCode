package scutil.option

final case class Option[+T](value: T = null.asInstanceOf[T]) extends AnyVal {
  def isEmpty = value == null
  def filter(f: T => Boolean): Option[T] = if (isEmpty || !f(value)) None else this
  def withFilter(f: T => Boolean): Option[T] = filter(f)
  def map[U](f: T => U): Option[U] = if (isEmpty) None else Option(f(value))
  def flatMap[U](f: T => Option[U]): Option[U] = if (isEmpty) None else f(value)
  def fold[U](f1: T => U, f2: () => U): U = if (isEmpty) f2() else f1(value)
  def toScalaOption: scala.Option[T] = if (isEmpty) scala.None else scala.Some(value)
  override def toString = if (isEmpty) "None" else "Some(" + value + ")"
}
