package kcacup
package models

import java.io.File

trait KcaFile {

  def basePath: String

  def filename: String

  def path = basePath + filename

  def file = new File(path)

  def name = filename.split("/").last

  def exists = file.exists && !file.isDirectory
}

case class PublicFile(filename: String) extends KcaFile {

  def basePath = "public/upload/"

  def publicPath = "upload/" + filename

  override def toString = publicPath
}

case class PrivateFile(filename: String) extends KcaFile {

  def basePath = "var/upload/"

  override def toString = path
}
