package kcacup

import models._

case class Standing (

  events: List[Event],

  nbSkips: Int

) {

  def top(max: Int): List[(User, Int)] = List()
}
