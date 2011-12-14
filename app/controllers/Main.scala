package kcacup
package controllers

import play.api._
import play.api.mvc._

object Main extends Controller with Front {

  private val env = new Env(Play.unsafeApplication.configuration)

  def index = Action {
    val events = env.eventRepo.findAll
    Ok(views.html.index(events))
  }

  def admin = Action {
    Ok(views.html.admin.index())
  }

}
