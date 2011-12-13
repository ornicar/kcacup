package kcacup
package repo

import models._
import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.dao._
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.Imports._

class EventRepo(collection: MongoCollection)
  extends SalatDAO[Event, ObjectId](collection)
  with Repo {

  collection.ensureIndex(DBObject("slug" -> 1), "slug_unicity", true)
  collection ensureIndex DBObject("createdAt" -> 1)

  def findAll = find(DBObject())
    .sort(DBObject("createdAt" -> -1))
    .toList

  def findOneBySlug(slug: String) = findOne(DBObject(
    "slug" -> slug
  ))

  def upsert(event: Event) =
    event.id some { id =>
      update(DBObject("_id" -> id), _grater.asDBObject(event))
    } none { save(event) }

}
