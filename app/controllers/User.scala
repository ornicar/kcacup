package kcacup
package controllers

import play.api._
import play.api.mvc._

object User extends Controller with Front {

  def newForm = Public { user => request =>
    Ok(views.html.user.newForm(user, form))
  }

  def create = Public { user => implicit request =>
    form.bindFromRequest.fold(
      form => Ok(views.html.user.newForm(user, form)),
      data => {
        val user = data.toUser
        env.userRepo save user
        redirectIndex.withSession("username" -> user.username)
      }
    )
  }

  def form = env.userForm.form
}
