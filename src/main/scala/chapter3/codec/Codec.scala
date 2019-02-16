package chapter3.codec

trait Codec[A] { self =>
  def encode(value: A): String

  def decode(value: String): A

  def imap[B](enc: A => B, dec: B => A): Codec[B] = {
    new Codec[B] {
      def encode(value: B): String = self.encode(dec(value))

      def decode(value: String): B = enc(self.decode(value))
    }
  }
}

object Codec {

  def encode[A](value: A)(implicit codec: Codec[A]): String = {
    codec.encode(value)
  }

  def decode[A](value: String)(implicit codec: Codec[A]): A = {
    codec.decode(value)
  }

}
