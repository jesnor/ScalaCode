package scutil.option

object None {
  def apply[T]() = Option[T]()
  def unapply[T](o: Option[T]) = o.isEmpty
  override def toString = "None"
}
