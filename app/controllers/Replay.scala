package kcacup
package controllers

import play.api._
import play.api.mvc._
import play.plugins.fileupload._

import models._

object Replay extends Controller with Front {

  //def create(slug: String) = Authenticated { me => implicit request =>
    //findEvent(slug) map { event =>
      //form.bindFromRequest.fold(
        //form => Ok(views.html.event.view(me.some, event, env.replayForm.form.some)),
        //data => {
          //Redirect(kcacup.controllers.routes.Event.view(slug))
        //}
      //)
    //} getOrElse BadRequest
  //}

  def create(slug: String) = Action[FileUploadContent](FileUploadContent.parser) { request: Request[FileUploadContent] =>
    import java.io._
    import org.apache.commons.fileupload.util.Streams
    (for {
      event <- (findEvent(slug) toRight "No event found")
      item <- (request.body.items.find(!_.isFormField) toRight "No file found")
      file = PrivateFile(item.getName.replace("..", "-"))
      bytes = Streams.copy(item.openStream(), new FileOutputStream(file.path), true)
      replay <- models.Replay("toto", file)
      newEvent <- (event withReplay replay)
    } yield newEvent).fold(println, env.eventRepo.upsert)

    Redirect(kcacup.controllers.routes.Event.view(slug))
  }

  private def findEvent(slug: String) = env.eventRepo findOneBySlug slug
}
