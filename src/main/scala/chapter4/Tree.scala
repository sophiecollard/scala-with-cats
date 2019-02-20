package chapter4

import cats.Monad
import cats.syntax.either._

import scala.annotation.tailrec

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Tree {

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  implicit val monad: Monad[Tree] = new Monad[Tree] {
    def pure[A](value: A): Tree[A] = {
      Leaf(value)
    }

    def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = {
      fa match {
        case Leaf(value) =>
          f(value)
        case Branch(left, right) =>
          val leftB = flatMap(left)(f)
          val rightB = flatMap(right)(f)
          Branch(leftB, rightB)
      }
    }

    def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = {
      flatMap(f(a)) {
        case Left(aa)  => tailRecM(aa)(f)
        case Right(bb) => Leaf(bb)
      }
    }
  }

}
