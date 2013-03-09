package scutil.option

object Some {
  def apply[T](v : T) = Option(v)
  def unapply[T](o : Option[T]) = o.toScalaOption
}
