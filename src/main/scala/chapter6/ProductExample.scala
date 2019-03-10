package chapter6

import cats.Monad
import cats.syntax.flatMap._ // for flatMap
import cats.syntax.functor._ // for map

object ProductExample {

  def product[M[_]: Monad, A, B](ma: M[A], mb: M[B]): M[(A, B)] = {
    ma.flatMap { a =>
      mb.map { b =>
        (a, b)
      }
    }
  }

  def product2[M[_]: Monad, A, B](ma: M[A], mb: M[B]): M[(A, B)] = {
    for {
      a <- ma
      b <- mb
    } yield (a, b)
  }

}
