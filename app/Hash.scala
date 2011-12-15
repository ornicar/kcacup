package kcacup

import hasher.Implicits._

object Hash {

  val salt = "94d6XcJcKwZ9"

  def encode(str: String) = str.salt(salt).sha1

  def compare(password: String, hash: String) =
    encode(password) == hash
}
