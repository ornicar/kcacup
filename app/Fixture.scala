package kcacup

import models._
import repo._
import com.mongodb.casbah.Imports._
import org.scala_tools.time.Imports._

/**
 * Initial set of data to be imported
 * in the sample application.
 */
class Fixture(eventRepo: EventRepo) {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def apply() = {

    def event(name: String, file: String, createdAt: Int, endsAt: Int) = Event(
      name = name,
      text = "So far, there has been two Across and three Elma World Cups which all collected hundreds of players. The basic idea of the cups is that we publish here a level once in a week or two, and you try to get as good time as your skills just allow you to get and then send the replay of your drive to us by a certain deadline, according to the rules. The results of the event are done some hours later and the better time you had, the better is of course your placement and also the more points you will get.",
      createdAt = DateTime.now - createdAt.weeks,
      endsAt = DateTime.now - createdAt.weeks + endsAt.week,
      levelFile = file + ".lev",
      imageFile = file + ".jpg",
      slug = StringHelper slugify name
    )

    eventRepo remove DBObject()
    Seq(
      event("Rillettes Crazy Lev", "rilletes", 5, 3),
      event("Dancing with dead rabbits", "rabbit", 4, 3),
      event("Delicate Octopus", "octopus", 4, 2),
      event("Getting serious", "serious", 2, 4),
      event("Pizza time", "pizza", 1, 2),
      event("The George Abitbol gap", "george", 0, 2)
    ) foreach eventRepo.save
  }

}
