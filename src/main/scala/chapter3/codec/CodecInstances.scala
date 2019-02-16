package chapter3.codec

trait CodecInstances {

  implicit val stringCodec: Codec[String] = new Codec[String] {
    def encode(value: String): String = value

    def decode(value: String): String = value
  }

  implicit val intCodec: Codec[Int] = {
    stringCodec.imap(_.toInt, _.toString)
  }

  implicit val booleanCodec: Codec[Boolean] = {
    stringCodec.imap(_.toBoolean, _.toString)
  }

}
