package kcacup
package models

import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import java.util.Date
import com.twitter.util.Time

case class Event(

  name: String,

  text: String,

  createdAtDate: Date,

  days: Int,

  image: PublicFile,

  slug: String,

  level: Level,

  replays: List[Replay] = Nil,

  @Key("_id") id: Option[ObjectId] = None

) extends RootObject with Chronological with Endable {

  def standing = Standing(List(this), 0)

  def bestReplay: Option[Replay] = None

  def top(nb: Int) = sortedReplays take nb

  def sortedReplays = replays sortBy (_.seconds)
}
