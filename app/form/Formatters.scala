package kcacup.form

import play.api.data._
import format.Formats._
import format.Formatter
import java.util.Date
import java.text.SimpleDateFormat
import org.scala_tools.time.Imports._

object Formatters {

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
}
