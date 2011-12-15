package kcacup
package form

import play.api.data._
import validation.Constraints._

object LoginForm {

  lazy val form = Form(
    of(Data.apply _, Data.unapply _)(
      "username" -> requiredText
    )
  )

  case class Data(
    username: String
  )
}
