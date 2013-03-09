package scutil.option

object ONone {
  def apply[T]() = Option[T](null.asInstanceOf[T])
  def unapply[T](o : Option[T]) = o.isEmpty
}
