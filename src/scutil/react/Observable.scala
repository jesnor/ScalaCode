package scutil.react

trait Observable[+T] {
  def subscribe(obs: Observer[T]): Subscription
}
