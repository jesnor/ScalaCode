package scutil.react.impl

import scutil.react._

abstract class AbstractObservableValue[T, U](ov: ObservableValue[T]) extends ObservableImpl[U] with ObservableValue[U] {
  private var sub: Subscription = null
  private var value: U = _

  def getValue(v: T): U
  def apply = if (sub == null) getValue(ov()) else value

  override def addObserver(obs: Observer[U]) {
    if (sub == null) {
      value = apply()

      sub = ov.subscribe { v =>
        val u = getValue(v)

        if (u != value) {
          value = u
          fireChange(value)
        }
      }
    }

    super.addObserver(obs)
  }

  override def removeObserver(obs: Observer[U]) {
    super.removeObserver(obs)

    if (observerCount == 0) {
      sub.dispose
      sub = null
      value = _: U
    }
  }
}
