package kcacup

import models._
import repo._
import com.mongodb.casbah.Imports._
import com.twitter.util.Time
import com.twitter.util.Duration
import com.twitter.conversions.time._

/**
 * Initial set of data to be imported
 * in the sample application.
 */
class Fixture(eventRepo: EventRepo) {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def apply() = {

    def event(
      name: String,
      createdAt: Int,
      weeks: Int,
      lev: String,
      img: String,
      replays: Seq[(String, String)]
    ) = Event(
      name = name,
      text = "So far, there has been two Across and three Elma World Cups which all collected hundreds of players. The basic idea of the cups is that we publish here a level once in a week or two, and you try to get as good time as your skills just allow you to get and then send the replay of your drive to us by a certain deadline, according to the rules. The results of the event are done some hours later and the better time you had, the better is of course your placement and also the more points you will get.",
      createdAtDate = (Time.now - (createdAt * 7).days).toDate,
      days = weeks * 7,
      level = Level(PublicFile("lev/" + lev)).fold(e => { println(e); sys.exit() }, identity),
      image = PublicFile(img),
      slug = StringHelper slugify name,
      replays = replays.map(a => {
          val (file: String, username: String) = a
          Replay(
            username = username,
            file = PublicFile("rec/" + file),
            createdAt = Time.now - (createdAt * 7).days + 3.days
          ).fold(e => { println(e); sys.exit() }, identity)
      }).toList
    )

    eventRepo remove DBObject()
    Seq(
      event("Rillettes Crazy Lev", 5, 3, "0mar14.lev", "lev1.png", Seq(
        "0mar14_456anp.rec" -> "anp",
        "0mar14_456tot.rec" -> "toto"
      )),
      event("Dancing with dead rabbits", 4, 3, "FMBa603.lev", "lev2.gif", Seq(
        "FMBa603_428tot.rec" -> "toto",
        "FMBa603_428Xar.rec" -> "Xar"
      )),
      event("Delicate Octopus", 4, 2, "jbl0192.lev", "lev3.jpg", Seq(
        "jbl0192_166tot.rec" -> "toto",
        "jbl0192_166Edd.rec" -> "Edd"
      )),
      event("Getting serious", 2, 4, "levlev31.lev", "lev4.gif", Seq(
        "levlev31_601Pab.rec" -> "Pablo",
        "levlev31_601tot.rec" -> "toto"
      )),
      event("Pizza time", 1, 2, "OLE test.lev", "1.jpg", Seq(
        "OLE test_603TL.rec" -> "TL",
        "OLE test_603tot.rec" -> "toto"
      )),
      event("The George Abitbol gap", 0, 2, "WolfI03.lev", "george.jpg", Seq(
        "WolfI03_599tot.rec" -> "toto",
        "WolfI03_599vil.rec" -> "Pablo"
      )),
      event("Guybrush way to heaven", 1, 2, "MC301.lev", "lev6.gif", Seq(
        "MC301adi.rec" -> "adi",
        "MC301Bjoern.rec" -> "Bjoern",
        "MC301Eddi.rec" -> "Eddi",
        "MC301GRob.rec" -> "GRob",
        "MC301J-sim.rec" -> "J",
        "MC301Kazan.rec" -> "Kazan",
        "MC301LazY.rec" -> "LazY",
        "MC301Lukazz.rec" -> "Lukazz",
        "MC301Madness.rec" -> "Madness",
        "MC301NightMar.rec" -> "NigthMar",
        "MC301Pab.rec" -> "Pab",
        "MC301romy4.rec" -> "romy4",
        "MC301ville_j.rec" -> "ville_j",
        "MC301Zweq.rec" -> "Zweq"
      ))
    ) foreach eventRepo.save
  }

}
