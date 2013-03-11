package scutil

import scutil.react.impl._

package object react {
  type Observer[-T] = T => Unit

  private class FilterClass[T](v: ObservableValue[Option[T]], f: T => Boolean) extends AbstractObservableValue[Option[T], Option[T]](v) {
    def getValue(v: Option[T]) = v.filter(f)
    override def toString = "filter(" + v + ")"
  }

  implicit class RichObservableOptionValue[T](val v: ObservableValue[Option[T]]) extends AnyVal {
    def filter(f: T => Boolean): ObservableValue[Option[T]] = new FilterClass(v, f)
  }

  private class MapClass[T, U](v: ObservableValue[T], f: T => U) extends AbstractObservableValue[T, U](v) {
    def getValue(v: T) = f(v)
    override def toString = "map(" + v + ")"
  }

  private class FlatMapClass[T, U](v: ObservableValue[T], f: T => ObservableValue[U]) extends ObservableImpl[U] with ObservableValue[U] {
    private var sub: Subscription = null
    private var sub2: Subscription = null
    private var ov: ObservableValue[U] = null
    private var value: U = _

    def apply = if (sub == null) f(v())() else value

    private def doSub(v2: ObservableValue[U]) {
      if (sub2 == null || v2 != ov) {
        if (sub2 != null)
          sub2.dispose

        ov = v2

        sub2 = ov.subscribe { nv =>
          if (nv != value) {
            value = nv
            fireChange(value)
          }
        }
      }
    }

    override def addObserver(obs: Observer[U]) {
      if (sub == null) {
        value = apply()

        sub = v.subscribe { v =>
          doSub(f(v))
          val nv = ov()

          if (nv != value) {
            value = nv
            fireChange(value)
          }
        }

        doSub(f(v()))
      }

      super.addObserver(obs)
    }

    override def removeObserver(obs: Observer[U]) {
      super.removeObserver(obs)

      if (observerCount == 0) {
        sub.dispose
        sub = null

        sub2.dispose
        sub2 = null

        ov = null
        value = _: U
      }
    }

    override def toString = "flatMap(" + v + ")"
  }

  implicit class RichObservableValue[T](val v: ObservableValue[T]) extends AnyVal {
    def map[U](f: T => U): ObservableValue[U] = new MapClass(v, f)
    def flatMap[U](f: T => ObservableValue[U]): ObservableValue[U] = new FlatMapClass(v, f)
  }

  def obsRef[T](name: String, v: T): ObservableVar[T] = new ObservableImpl[T] with ObservableVar[T] {
    private var value: T = v

    def apply = value

    def apply(v: T) {
      if (v != value) {
        value = v
        fireChange(value)
      }
    }

    override def toString = name
  }
}