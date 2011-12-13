package kcacup

import org.scala_tools.time.Imports._
import org.joda.time.format.PeriodFormatterBuilder

object StringHelper extends StringHelper

trait StringHelper {

  private object Slugifier {
    def slugify(text: String) = trimUnderscores(removeNonWord(text))

    def removeNonWord(text: String) = """\W+""".r.replaceAllIn(text, "_")

    def trimUnderscores(text: String) = """^\_|\_$""".r.replaceAllIn(text, "")
  }

  def slugify(text: String): String = Slugifier.slugify(text)

  def formatDate(date: DateTime) = dateFormatter print date

  def formatInterval(interval: Interval) = periodFormatter print interval.toPeriod

  def formatDuration(duration: Duration) = pluralize("day", duration.getStandardDays.toInt)

  def pluralize(s: String, n: Int) = "%d %s%s".format(n, s, if (n > 1) "s" else "")

  private lazy val dateFormatter = DateTimeFormat forPattern "dd MMMM yyy"

  private lazy val periodFormatter = new PeriodFormatterBuilder()
      .appendMonths()
      .appendSuffix(" month", " months")
      .appendSeparator(", ")
      .appendWeeks()
      .appendSuffix(" week", " weeks")
      .appendSeparator(" and ")
      .appendDays()
      .appendSuffix(" day", " days")
      .toFormatter()
}
