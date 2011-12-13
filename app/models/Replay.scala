package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class Replay(

  @Key("_id") id: Int,

  user: Player,

  event: Event,

  levelId: Int,

  file: String,

  createdAt: Date,

  centiseconds: Long
)
