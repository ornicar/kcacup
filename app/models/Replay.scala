package kcacup
package models

import scala.sys.process.Process
import org.scala_tools.time.Imports._
import scala.util.control.Exception.allCatch

case class Replay(

  username: String,

  file: PublicFile,

  createdAt: DateTime,

  seconds: Double,

  levelId: String
) {
}

object Replay {

  def apply(
    username: String,
    file: PublicFile,
    createdAt: DateTime
  ): Either[String, Replay] = for {
    infos <- parseFile(file)
    (levelId, seconds) = infos
  } yield Replay(
    username = username,
    file = file,
    createdAt = createdAt,
    seconds = seconds,
    levelId = levelId
  )

  private def parseFile(file: PublicFile): Either[String, (String, Double)] = {

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
