package kcacup
package form

import play.api.data._
import format.Formats._
import validation.Constraints._
import org.scala_tools.time.Imports._

import models._

object EventForm {

  lazy val form = Form(
    of(Data.apply _, Data.unapply _)(
      "name" -> requiredText,
      "text" -> requiredText,
      "levelFile" -> requiredText,
      "imageFile" -> requiredText,
      "days" -> of[Int]
    )
  )

  case class Data(
    name: String,
    text: String,
    levelFile: String,
    imageFile: String,
    days: Int
  ) {
    def toEvent = Event(
      name = name,
      text = text,
      level = Level(PublicFile(levelFile)).right.get,
      image = PublicFile(imageFile),
      createdAt = DateTime.now,
      days = days,
      slug = StringHelper slugify name
    )
    def toEvent(event: Event): Event = event.copy(
      name = name,
      text = text,
      level = Level(PublicFile(levelFile)).right.get,
      image = PublicFile(imageFile),
      days = days,
      slug = StringHelper slugify name
    )
  }

  object Data {

    def fromEvent(event: Event) = Data(
      name = event.name,
      text = event.text,
      levelFile = event.level.file.publicPath,
      imageFile = event.image.publicPath,
      days = event.days
    )
  }

}
