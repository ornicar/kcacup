package kcacup
package models

import java.text.SimpleDateFormat
import java.util.Date
import com.twitter.util.Time

trait Chronological extends Ordered[Chronological] {

  def createdAtDate: Date

  def createdAt: Time = Time(createdAtDate)

  def compare(other: Chronological) =
    this.createdAt compare other.createdAt

  def showCreatedAt =
    new SimpleDateFormat("dd MMMM yyy") format createdAtDate
}
