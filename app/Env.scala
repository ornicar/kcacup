package kcacup

import play.api.Configuration
import repo._
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.conversions.scala._

class Env(configuration: Configuration) {

  RegisterJodaTimeConversionHelpers()

  lazy val eventRepo = new EventRepo(mongodb("event"))

  lazy val postRepo = new PostRepo(mongodb("post"))

  lazy val cupRepo = new CupRepo(mongodb("cup"))

  lazy val replayRepo = new ReplayRepo(mongodb("replay"))

  lazy val playerRepo = new PlayerRepo(mongodb("player"))

  lazy val fixture = new Fixture(eventRepo)

  // load fixture if DB is empty
  if (true || 0 == eventRepo.count()) fixture()

  private lazy val mongoConnection = MongoConnection(
    conf("mongo.host") | "localhost",
    conf("mongo.port") some (_.toInt) none 27017
  )

  private lazy val mongodb = mongoConnection(
    conf("mongo.dbname") | "kcacup"
  )

  private def conf(key: String) = configuration get key map (_.value)
}
