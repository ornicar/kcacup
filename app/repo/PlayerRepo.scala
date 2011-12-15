package kcacup
package repo

import models._
import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.dao._
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.Imports._

class UserRepo(collection: MongoCollection)
  extends SalatDAO[User, String](collection)
  with Repo {

  def findAll = find(DBObject()).toList

  def findOneByUsername(username: String) = findOne(
    DBObject("_id" -> username)
  )

  def findOneLogin(username: String, password: String) = for {
    user <- findOneByUsername(username)
    if (user comparePassword password)
  } yield user
}
