package chapter3

trait Printable[A] { self =>

  def format(value: A): String

  def contramap[B](f: B => A): Printable[B] = {
    new Printable[B] {
      def format(value: B): String = {
        self.format(f(value))
      }
    }
  }

}
