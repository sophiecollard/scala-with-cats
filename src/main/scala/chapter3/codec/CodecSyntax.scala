package chapter3.codec

trait CodecSyntax {

  implicit class CodecOps[A](value: A) {
    def encode(implicit codec: Codec[A]): String = {
      codec.encode(value)
    }
  }

  implicit class StringOps(value: String) {
    def decode[A](implicit codec: Codec[A]): A = {
      codec.decode(value)
    }
  }

}
