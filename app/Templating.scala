package kcacup

import models._
import play.api.templates._

object Templating extends Conversions with StringHelper {

  def time(time: String): Html =
    Html("""<span class="time">%s</span>""" format time)

  def time(replay: Replay): Html = time(replay.showTime)
}
