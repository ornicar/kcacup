package kcacup

import play.api.Configuration
import repo._
import form._
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.conversions.scala._

class Env(configuration: Configuration) {

  lazy val eventRepo = new EventRepo(mongodb("event"))

  lazy val postRepo = new PostRepo(mongodb("post"))

  lazy val cupRepo = new CupRepo(mongodb("cup"))

  lazy val userRepo = new UserRepo(mongodb("user"))

  lazy val loginForm = new LoginForm(userRepo)

  lazy val userForm = new UserForm(userRepo)

  lazy val timeline = new Timeline(eventRepo)

  lazy val fixture = new Fixture(eventRepo, userRepo)

  // load fixture if DB is empty
  if (true || 0 == eventRepo.count()) fixture()

  private lazy val mongoConnection = MongoConnection(
    conf("mongo.host") | "127.0.0.1",
    conf("mongo.port") some (_.toInt) none 27017
  )

  private lazy val mongodb = mongoConnection(
    conf("mongo.dbname") | "kcacup"
  )

  private def conf(key: String) = configuration get key map (_.value)
}
