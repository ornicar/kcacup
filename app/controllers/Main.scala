package kcacup
package controllers

import play.api._
import play.api.mvc._

object Main extends Controller with Front {

  def index = Public { user => request =>
    val events = env.eventRepo.findAll
    Ok(views.html.index(user, events))
  }

  def admin = Action {
    Ok(views.html.admin.index())
  }

}
