package kcacup
package controllers

import play.api._
import play.api.mvc._

import form.UserForm._

object User extends Controller with Front {

  def newForm = Public { user => request =>
    Ok(views.html.user.newForm(user))
  }
}
