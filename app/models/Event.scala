package kcacup
package models

import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import org.scala_tools.time.Imports._

case class Event(

  name: String,

  text: String,

  createdAt: DateTime,

  endsAt: DateTime,

  levelFile: String,

  imageFile: String,

  slug: String,

  @Key("_id") id: Option[ObjectId] = None

) extends RootObject with Chronological with Endable {

  def standing = Standing(List(this), 0)

  def image = Image(imageFile)

  def level = Level(levelFile)
}
