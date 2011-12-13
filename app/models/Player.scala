package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class Player (

  @Key("_id") username: String,

  password: String,

  createdAt: Date
)
