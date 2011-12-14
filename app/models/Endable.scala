package kcacup
package models

import org.scala_tools.time.Imports._

trait Endable extends Chronological {

  def days: Int

  def ended: Boolean = DateTime.now >= endsAt

  def endsAt: DateTime = createdAt + days.days

  def duration: Duration = (createdAt to endsAt).toDuration

  def remainingInterval: Option[Interval] =
    if (ended) None else (DateTime.now to endsAt).some

  def remainingDuration: Option[Duration] =
    remainingInterval map (_.toDuration)
}
