package chapter3

import cats.Functor

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Tree {

  implicit class TreeOps[A](tree: Tree[A]) {
    def map[B](f: A => B): Tree[B] = {
      functor.map(tree)(f)
    }
  }

  implicit val functor: Functor[Tree] = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      case Leaf(a)             => Leaf(f(a))
    }
  }

}
