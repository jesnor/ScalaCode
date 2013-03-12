package scutil.option

/**
 * value has to have type Any as it's not allowed to define a value of type Nothing
 */
final case class Option[+T](value: Any = null) extends AnyVal with Product with Serializable {
  private def unsafeGet = value.asInstanceOf[T]
  def isEmpty: Boolean = value == null
  def isDefined: Boolean = !isEmpty
  def nonEmpty = isDefined
  def contains[U >: T](elem: U): Boolean = !isEmpty && value == elem
  def exists(p: T => Boolean): Boolean = !isEmpty && p(unsafeGet)
  def forall(p: T => Boolean): Boolean = isEmpty || p(unsafeGet)
  def orElse[U >: T](alternative: => Option[U]): Option[U] = if (isEmpty) alternative else this
  def collect[U](pf: PartialFunction[T, U]): Option[U] = if (!isEmpty) pf.lift(unsafeGet).toAnyValOption else None
  def iterator: Iterator[T] = if (isEmpty) collection.Iterator.empty else collection.Iterator.single(unsafeGet)
  def toList: List[T] = if (isEmpty) List() else new ::(unsafeGet, Nil)
  def toRight[X](left: => X) = if (isEmpty) Left(left) else Right(value)
  def toLeft[X](right: => X) = if (isEmpty) Right(right) else Left(value)
  def getOrElse[U >: T](default: => U): U = if (isEmpty) default else unsafeGet
  def orNull[U >: T](implicit ev: Null <:< U): U = unsafeGet
  def filter(p: T => Boolean): Option[T] = if (isEmpty || p(unsafeGet)) this else None
  def filterNot(p: T => Boolean): Option[T] = if (isEmpty || !p(unsafeGet)) this else None
  def withFilter(f: T => Boolean): Option[T] = filter(f)
  def map[U](f: T => U): Option[U] = if (isEmpty) None else f(unsafeGet).some
  def flatMap[U](f: T => Option[U]): Option[U] = if (isEmpty) None else f(unsafeGet)
  def foreach[U](f: T => U): Unit = if (!isEmpty) f(unsafeGet)
  def fold[U](ifEmpty: => U)(f: T => U): U = if (isEmpty) ifEmpty else f(unsafeGet)
  def flatten[U](implicit ev: T <:< Option[U]): Option[U] = if (isEmpty) None else ev(unsafeGet)
  def toScalaOption: scala.Option[T] = if (isEmpty) scala.None else scala.Some(unsafeGet)
  override def toString = if (isEmpty) "None" else "Some(" + unsafeGet + ")"
}

object Option {
  import scala.language.implicitConversions
  implicit def option2Iterable[T](xo: Option[T]): Iterable[T] = xo.toList
  def empty[T]: Option[T] = None
}
