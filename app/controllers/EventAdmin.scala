package kcacup
package controllers

import play.api._
import play.api.mvc._

import models._
import form.EventForm._

object EventAdmin extends Controller {

  private val env = new Env(Play.unsafeApplication.configuration)

  def index = Action {
    Ok(views.html.event.admin.index(findEvents))
  }

  def newForm = Action {
    Ok(views.html.event.admin.newForm(form))
  }

  def create = Action { implicit request =>
    form.bindFromRequest.fold(
      form => Ok(views.html.event.admin.newForm(form)),
      data => saveAndRedirect(data toEvent)
    )
  }

  def editForm(slug: String) = Action {
    findEvent(slug) map { event =>
      Ok(views.html.event.admin.editForm(
        event,
        form fill Data.fromEvent(event)
      ))
    } getOrElse redirectIndex
  }

  def update(slug: String) = Action { implicit request =>
    findEvent(slug) some { event =>
      form.bindFromRequest.fold(
        form => Ok(views.html.event.admin.editForm(event, form)),
        data => saveAndRedirect(data toEvent event)
      )
    } none redirectIndex
  }

  def delete(slug: String) = Action {
    findEvent(slug) foreach env.eventRepo.remove
    redirectIndex
  }

  private def saveAndRedirect(event: Event) = {
    env.eventRepo upsert event
    redirectIndex
  }

  private def redirectIndex =
    Redirect(kcacup.controllers.routes.EventAdmin.index)

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug

  private def findEvents = env.eventRepo.findAll
}
