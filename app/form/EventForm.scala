package kcacup
package form

import play.api.data._
import format.Formats._
import format.Formatter
import validation.Constraints._
import java.util.Date
import java.text.SimpleDateFormat
import org.scala_tools.time.Imports._

import models._

object EventForm {

  /**
   * Formatter for the `java.util.Date` type.
   *
   * @param pattern a date pattern, as specified in `java.text.SimpleDateFormat`.
   */
  def dateFormat(pattern: String): Formatter[DateTime] = new Formatter[DateTime] {

    override val format = Some("format.date", Seq(pattern))

    def bind(key: String, data: Map[String, String]) =
      stringFormat.bind(key, data).right.flatMap { s =>
        scala.util.control.Exception.allCatch[Date]
          .either(new SimpleDateFormat(pattern).parse(s))
          .left.map(e => Seq(FormError(key, "error.date", Nil)))
          .right.map(d => new DateTime(d.getTime))
      }


    def unbind(key: String, value: DateTime) = Map(key -> new SimpleDateFormat(pattern).format(value.toDate))
  }

  /**
   * Default formatter for the `java.util.Date` type with pattern `yyyy-MM-dd`.
   *
   * @param pattern a date pattern as specified in `java.text.SimpleDateFormat`.
   */
  implicit val dateFormat: Formatter[DateTime] = dateFormat("yyyy-MM-dd")

  lazy val form = Form(
    of(Data.apply _, Data.unapply _)(
      "name" -> requiredText,
      "text" -> requiredText,
      "levelFile" -> requiredText,
      "imageFile" -> requiredText,
      "endsAt" -> of[DateTime]
    )
  )

  case class Data(
    name: String,
    text: String,
    levelFile: String,
    imageFile: String,
    endsAt: DateTime
  ) {
    def toEvent = Event(
      name = name,
      text = text,
      levelFile = levelFile,
      imageFile = imageFile,
      createdAt = DateTime.now,
      endsAt = endsAt,
      slug = StringHelper slugify name
    )
    def toEvent(event: Event): Event = event.copy(
      name = name,
      text = text,
      levelFile = levelFile,
      imageFile = imageFile,
      endsAt = endsAt,
      slug = StringHelper slugify name
    )
  }

  object Data {

    def fromEvent(event: Event) = Data(
      name = event.name,
      text = event.text,
      levelFile = event.levelFile,
      imageFile = event.imageFile,
      endsAt = event.endsAt
    )
  }

}
