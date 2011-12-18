package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class User (

  @Key("_id") id: String,

  username: String,

  passwordHash: String,

  createdAtDate: Date
) {

  def comparePassword(p: String) = Hash.compare(p, passwordHash)
}

object User {

  def apply(username: String, password: String): User = User(
    id = normalizeUsername(username),
    username = username,
    passwordHash = Hash encode password,
    createdAtDate = new Date
  )

  def normalizeUsername(username: String) =
    """[^\w]""".r.replaceAllIn(username.toLowerCase, "")
}
