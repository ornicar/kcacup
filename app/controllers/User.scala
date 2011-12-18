package kcacup
package controllers

import play.api._
import play.api.mvc._

object User extends Controller with Front {

  def view(username: String) = Public { me => request =>
    env.userRepo(username) map { u =>
      Ok(views.html.user.view(me, u, env.timeline ofUser u))
    } getOrElse BadRequest
  }

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug

  def newForm = Public { me => request =>
    Ok(views.html.user.newForm(me, form))
  }

  def create = Public { me => implicit request =>
    form.bindFromRequest.fold(
      form => Ok(views.html.user.newForm(me, form)),
      data => {
        val user = data.toUser
        env.userRepo save user
        redirectIndex.withSession("username" -> user.username)
      }
    )
  }

  private def form = env.userForm.form
}
