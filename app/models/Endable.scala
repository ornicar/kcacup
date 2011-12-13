package kcacup
package models

import org.scala_tools.time.Imports._

trait Endable extends Chronological {

  def endsAt: DateTime

  def ended: Boolean = DateTime.now >= endsAt

  def duration: Duration = (createdAt to endsAt).toDuration

  def remainingInterval: Option[Interval] =
    if (ended) None else (DateTime.now to endsAt).some
}
