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

  def bestReplay: Option[Replay] = sortedReplays.headOption

  def top(nb: Int) = sortedReplays take nb

  lazy val sortedReplays = replays sortBy (_.centis)

  def recentReplays(nb: Int) = replays take nb

  def usernames = (replays map (_.username)).distinct

  def nbUsers = usernames.size

  def withReplay(replay: Replay): Either[String, Event] =
    (replay.levelId != level.id) either "Wrong level ID" or copy(replays = replay :: replays)
}
