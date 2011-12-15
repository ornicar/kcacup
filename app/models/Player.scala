package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class User (

  @Key("_id") username: String,

  passwordHash: String,

  createdAtDate: Date
) {

  def comparePassword(p: String) = Hash.compare(p, passwordHash)
}

object User {

  def apply(username: String, password: String): User = User(
    username = username,
    passwordHash = Hash encode password,
    createdAtDate = new Date
  )
}
