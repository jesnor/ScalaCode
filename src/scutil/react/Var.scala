package scutil.react

trait Var[T] extends Value[T] {
  def apply(v: T)
}
