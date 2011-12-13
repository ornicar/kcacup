package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class Cup (

  @Key("_id") id: Int,

  name: String,

  text: String,

  events: List[Event],

  nbSkips: Int,

  createdAt: Date
) {

  def standing: Standing = Standing(events, nbSkips)
}
