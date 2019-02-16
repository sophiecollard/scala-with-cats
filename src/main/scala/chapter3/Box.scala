package chapter3

import chapter3.codec.Codec
import chapter3.printable.Printable

final case class Box[A](value: A)

object Box {

  // Contravariant functor instance declaration using contramap
  implicit def printable[A](implicit ev: Printable[A]): Printable[Box[A]] = {
    ev.contramap(_.value)
  }

  // Invariant functor instance declaration using imap
  implicit def codec[A](implicit ev: Codec[A]): Codec[Box[A]] = {
    ev.imap(Box.apply, _.value)
  }

}
