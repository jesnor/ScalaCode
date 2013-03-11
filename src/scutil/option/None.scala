package scutil.option

object None extends OptionBase {
  def apply[T]() = Option[T](null.asInstanceOf[T])
  def unapply[T](o: Option[T]) = o.isEmpty
  override def toString = "None"
}
