package kcacup

import models._

case class Standing (

  events: List[Event],

  nbSkips: Int

) {

  def top(max: Int): List[(Player, Int)] = List()
}
