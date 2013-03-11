package scutil.option

final case class Option[+T](value: T = null.asInstanceOf[T]) extends AnyVal with Product with Serializable {
  def isEmpty: Boolean = value == null
  def isDefined: Boolean = !isEmpty
  def nonEmpty = isDefined
  def contains[U >: T](elem: U): Boolean = !isEmpty && value == elem
  def exists(p: T => Boolean): Boolean = !isEmpty && p(value)
  def forall(p: T => Boolean): Boolean = isEmpty || p(value)
  def orElse[U >: T](alternative: => Option[U]): Option[U] = if (isEmpty) alternative else this
  def collect[U](pf: PartialFunction[T, U]): Option[U] = if (!isEmpty) pf.lift(value).toAnyValOption else None
  def iterator: Iterator[T] = if (isEmpty) collection.Iterator.empty else collection.Iterator.single(value)
  def toList: List[T] = if (isEmpty) List() else new ::(value, Nil)
  def toRight[X](left: => X) = if (isEmpty) Left(left) else Right(value)
  def toLeft[X](right: => X) = if (isEmpty) Right(right) else Left(value)
  def getOrElse[U >: T](default: => U): U = if (isEmpty) default else value
  def orNull[U >: T](implicit ev: Null <:< U): U = value
  def filter(p: T => Boolean): Option[T] = if (isEmpty || p(value)) this else None
  def filterNot(p: T => Boolean): Option[T] = if (isEmpty || !p(value)) this else None
  def withFilter(f: T => Boolean): Option[T] = filter(f)
  def map[U](f: T => U): Option[U] = if (isEmpty) None else Option(f(value))
  def flatMap[U](f: T => Option[U]): Option[U] = if (isEmpty) None else f(value)
  def foreach[U](f: T => U): Unit = if (!isEmpty) f(value)
  def fold[U](ifEmpty: => U)(f: T => U): U = if (isEmpty) ifEmpty else f(value)
  def flatten[U](implicit ev: T <:< Option[U]): Option[U] = if (isEmpty) None else ev(value)
  def toScalaOption: scala.Option[T] = if (isEmpty) scala.None else scala.Some(value)
  override def toString = if (isEmpty) "None" else "Some(" + value + ")"
}

object Option {
  import scala.language.implicitConversions
  implicit def option2Iterable[T](xo: Option[T]): Iterable[T] = xo.toList
  def empty[T] : Option[T] = None
}
