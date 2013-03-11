package scutil.react.impl

import scutil.react._

trait ObservableImpl[T] extends Observable[T] {
  private var observers: Array[Observer[T]] = null

  def observerCount = if (observers == null) 0 else observers.size

  protected def addObserver(obs: Observer[T]) {
    println("Add: " + System.identityHashCode(obs) + " to " + this + "(" + System.identityHashCode(this) + ")")
    
    if (observers == null) {
      observers = Array(obs)
    }
    else {
      val no = new Array[Observer[T]](observers.length + 1)
      System.arraycopy(observers, 0, no, 0, observers.length)
      no(observers.length) = obs
      observers = no
    }
  }

  protected def removeObserver(obs: Observer[T]) {
    println("Remove: " + System.identityHashCode(obs) + " from " + this + "(" + System.identityHashCode(this) + ")")
    
    if (observers.length == 1) {
      observers = null
    }
    else {
      val i = observers.indexOf(obs)
      val no = new Array[Observer[T]](observers.length - 1)
      System.arraycopy(observers, 0, no, 0, i)
      System.arraycopy(observers, i + 1, no, i, observers.length - i - 1)
      observers = no
    }

  }

  def subscribe(obs: Observer[T]) = {
    addObserver(obs)

    new Subscription {
      def dispose = removeObserver(obs)
    }
  }

  protected def fireChange(v: T) {
    for (obs <- observers)
      obs(v)
  }
}

