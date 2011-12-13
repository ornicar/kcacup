package kcacup.models

import com.mongodb.casbah.Imports._

trait RootObject {

  def id: Option[ObjectId]

  def isNew = id.isEmpty
}
