package kcacup
package models

import java.io.File

trait PublicFile {

  def filename: String

  def publicPath = "upload/" + filename

  def path = "public/upload/" + filename

  def file = new File(path)

  def exists = file.exists && !file.isDirectory
}
