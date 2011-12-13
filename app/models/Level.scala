package kcacup
package models

import scala.sys.process.Process

case class Level(filename: String) extends PublicFile {

  case class Metadata(
    name: String,
    id: Int
  ) {
    override def toString = name
  }

  lazy val metadata = {
    val process = Process("cpp/totolev " + path)
    process! match {
      case 0 => {
        val lines = (process!!).lines.toArray
        Metadata(lines(1), lines(0).toInt).right
      }
      case x => ("Exit: " + x).left
    }
  }
}
