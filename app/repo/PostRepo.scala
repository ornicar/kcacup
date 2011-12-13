package kcacup
package repo

import models._
import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.dao._
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.Imports._

class PostRepo(collection: MongoCollection)
  extends SalatDAO[Post, ObjectId](collection)
  with Repo {

  def findAll = find(DBObject()).toList
}
