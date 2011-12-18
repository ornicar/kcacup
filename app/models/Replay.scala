package kcacup
package models

import scala.sys.process.Process
import scala.util.control.Exception.allCatch
import java.util.Date
import com.twitter.util.Time
import com.twitter.util.Duration
import com.twitter.conversions.time._

case class Replay(

  username: String,

  file: PrivateFile,

  createdAtDate: Date,

  centis: Int,

  levelId: String

) extends Chronological {

  def millis: Int = centis * 10

  def duration: Duration = millis.milliseconds

  def showTime = "%d:%02d:%02d".format(
    centis/6000,
    (centis%6000)/100,
    centis%100
  )
}

object Replay {

  def apply(
    username: String,
    file: PrivateFile,
    createdAt: Time
  ): Either[String, Replay] = for {
    infos <- parseFile(file)
    (levelId, centis) = infos
  } yield Replay(
    username = username,
    file = file,
    createdAtDate = createdAt.toDate,
    centis = centis.toInt,
    levelId = levelId
  )

  def apply(
    username: String,
    file: PrivateFile
  ): Either[String, Replay] = apply(username, file, Time.now)

  private def parseFile(file: KcaFile): Either[String, (String, Double)] = {

    val process = Process("cpp/totorec", Seq(file.path))

    process! match {
      case 0 => {
        val lines = (process!!).lines.toArray
        def error(msg: Any) = "[%s] %s - %s".format(file, msg, lines mkString "\n")
        lines.size match {
          case n if n < 4 => error("Not enough lines").left
          case n => {
            val nbObjects = lines(2).toInt
            if (n != nbObjects + 3) error("Not enough times").left
            else allCatch.either(
              (lines(0), lines(n - 1).split(";").head.toDouble)
            ).left map error
          }
        }
      }
      case x => (file.path + " Exit: " + x).left
    }
  }
}
