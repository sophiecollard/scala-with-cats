package chapter3

import chapter3.printable.Printable

final case class Box[A](value: A)

object Box {

  implicit def printable[A](implicit ev: Printable[A]): Printable[Box[A]] = {
    ev.contramap(_.value)
  }

}