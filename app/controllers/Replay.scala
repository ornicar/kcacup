package kcacup
package controllers

import play.api._
import play.api.mvc._
import play.plugins.fileupload._

import java.io._
import org.apache.commons.fileupload.util.Streams

import models._

object Replay extends Controller with Front {

  def create(slug: String) = Action[FileUploadContent](FileUploadContent.parser) {
    request: Request[FileUploadContent] => (for {
      me     <- getUser(request)
      event  <- findEvent(slug)
    } yield saveReplay(me, event, request).fold(
        err => Ok(views.html.event.view(me.some, event, err.some)),
        _ => Redirect(kcacup.controllers.routes.Event.view(slug))
      )
    ) getOrElse BadRequest
  }

  private def saveReplay(user: User, event: Event, request: Request[FileUploadContent]) = for {
    item   <- request.body.items.find(!_.isFormField) toRight "No file found"
    filename = item.getName.replace("..", "-")
    file   <- (filename.isEmpty) either "No file found" or PrivateFile(filename)
    bytes  <- Streams.copy(item.openStream, new FileOutputStream(file.path), true).right
    replay <- models.Replay("toto", file)
    event2 <- event withReplay replay
  } yield env.eventRepo upsert event2

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug
}
