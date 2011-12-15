package kcacup
package controllers

import play.api._
import play.api.mvc._

import models._

object Event extends Controller with Front {

  private val env = new Env(Play.unsafeApplication.configuration)

  def view(slug: String) = Action {
    findEvent(slug) map { event =>
      Ok(views.html.event.view(event))
    } getOrElse BadRequest
  }

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug
}
