package kcacup
package models

import com.twitter.util.Time
import com.twitter.util.Duration
import com.twitter.conversions.time._

trait Endable extends Chronological {

  def days: Int

  def ended: Boolean = Time.now.inMilliseconds >= endsAt.inMilliseconds

  def endsAt: Time = createdAt + days.days

  def duration: Duration = createdAt until endsAt

  def remainingDuration: Option[Duration] =
    if (ended) None else (Time.now until endsAt).some
}
