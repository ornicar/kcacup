package kcacup
package controllers

import play.api._
import play.api.mvc._

import models._

object Event extends Controller with Front {

  def view(slug: String) = Public { user => request =>
    findEvent(slug) map { event =>
      Ok(views.html.event.view(user, event))
    } getOrElse BadRequest
  }

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug
}
