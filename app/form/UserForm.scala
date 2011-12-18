package kcacup
package form

import play.api.data._
import validation.Constraints._

import repo.UserRepo
import models._

class UserForm(userRepo: UserRepo) {

  lazy val form = Form(
    of(UserForm.Data.apply _, UserForm.Data.unapply _)(
      "username" -> requiredText.verifying(
        minLength(3),
        maxLength(200),
        pattern("""^\w+$""".r)
      ),
      "password" -> requiredText.verifying(
        minLength(4),
        maxLength(200)
      )
    ) verifying("The username is taken already", {
      data => !(userRepo exists data.username)
    })
  )
}

object UserForm {

  case class Data(
    username: String,
    password: String
  ) {
    def toUser = User(username, password)
  }
}
