package chapter4

object Monads {

  type Id[A] = A

  def pure[A](value: A): Id[A] = value

  def map[A,B](fa: Id[A])(f: A => B): Id[B] = {
    f(fa)
  }

  def flatMap[A,B](fa: Id[A])(f: A => Id[B]): Id[B] = {
    f(fa)
  }

}
