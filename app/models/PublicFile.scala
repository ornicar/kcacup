package kcacup
package models

import java.io.File

case class PublicFile(filename: String) {

  def publicPath = "upload/" + filename

  def path = "public/upload/" + filename

  def file = new File(path)

  def name = filename.split("/").last

  def exists = file.exists && !file.isDirectory

  override def toString = publicPath
}
