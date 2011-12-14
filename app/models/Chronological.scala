package kcacup
package models

import java.text.SimpleDateFormat
import java.util.Date
import com.twitter.util.Time

trait Chronological extends Ordered[Chronological] {

  def createdAt: DateTime

  def compare(other: Chronological) =
    //this.createdAt.getTime compare other.createdAt.getTime
    this.createdAt compare other.createdAt

  def showCreatedAt =
    new SimpleDateFormat("dd MMMM yyy") format createdAt
}
