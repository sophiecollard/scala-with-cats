package chapter4

import cats.Eval

object EvalExample {

  // Unsafe implementation
  def foldRight[A,B](as: List[A], acc: B)(f: (A, B) => B): B = {
    as match {
      case Nil =>
        acc
      case head :: tail =>
        f(head, foldRight(tail, acc)(f))
    }
  }

  // Stack-safe implementation using Eval.defer
  def safeFoldRight[A,B](as: List[A], acc: B)(f: (A, B) => B): Eval[B] = {
    as match {
      case Nil =>
        Eval.now { acc }
      case head :: tail =>
        Eval.defer { safeFoldRight(tail, acc)(f).map(f(head, _)) }
    }
  }

}
