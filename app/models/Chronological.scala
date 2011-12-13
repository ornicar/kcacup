package kcacup
package models

import org.scala_tools.time.Imports._
import java.text.SimpleDateFormat

trait Chronological extends Ordered[Chronological] {

  def createdAt: DateTime

  def compare(other: Chronological) =
    //this.createdAt.getTime compare other.createdAt.getTime
    this.createdAt compare other.createdAt

  def showCreatedAt =
    new SimpleDateFormat("dd MMMM yyy") format createdAt
}
