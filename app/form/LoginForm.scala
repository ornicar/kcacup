package kcacup
package form

import play.api.data._
import validation.Constraints._

import repo.UserRepo

class LoginForm(userRepo: UserRepo) {

  def form = Form(
    of(Data.apply _, Data.unapply _)(
      "username" -> requiredText,
      "password" -> requiredText
    ) verifying("Invalid user name or password", {
      data => data.user.isDefined
    })
  )

  case class Data(username: String, password: String) {

    //val user = userRepo.findOneLogin(username, password)
    val user = userRepo.findOneByUsername(username)
  }

}
