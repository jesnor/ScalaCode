package scutil.react

trait Value[+T] {
  def apply(): T
}
