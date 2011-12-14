package kcacup
package models

import scala.sys.process.Process

case class Level(

  file: PublicFile,

  id: String,

  name: String,

  nbObjects: Int
)

object Level {

  def apply(file: PublicFile): Either[String, Level] = for {
    infos <- parseFile(file)
    (id, name, nbObjects) = infos
  } yield Level(
    file = file,
    id = id,
    name = name,
    nbObjects = nbObjects
  )

  private def parseFile(file: PublicFile): Either[String, (String, String, Int)] = {
    val process = Process("cpp/totolev", Seq(file.path))
    process! match {
      case 0 => {
        val lines = (process!!).lines.toArray
        (lines(0), lines(1), lines(2).toInt).right
      }
      case x => ("Exit: " + x).left
    }
  }
}
