package kcacup
package controllers

import play.api._
import play.api.mvc._

object Main extends Controller {

  //private val env = new Env(Play.unsafeApplication.configuration)

  def index = Action {
    Ok(views.html.index())
  }

  def admin = Action {
    Ok(views.html.admin.index())
  }

}
