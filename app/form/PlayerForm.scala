package kcacup
package form

import play.api.data._
import validation.Constraints._

import models._

object PlayerForm {

  lazy val form = Form(
    of(Data.apply _, Data.unapply _)(
      "username" -> requiredText,
      "password" -> requiredText
    )
  )

  case class Data(
    username: String,
    password: String
  )
}
