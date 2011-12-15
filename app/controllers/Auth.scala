package kcacup
package controllers

import play.api._
import play.api.mvc._

import form.LoginForm._

object Auth extends Controller with Front {

  // Login action: here I just bind a (non empty) username
  // from the query and put it in the session
  def login = Action { implicit request =>
    form.bindFromRequest.fold(
      f => redirectIndex,
      data => redirectIndex.withSession("username" -> data.username)
    )
  }

  // Logout action: clear the session and redirect to the home page
  def logout = Action {
    redirectIndex.withNewSession
  }
}
