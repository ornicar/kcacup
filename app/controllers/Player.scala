package kcacup
package controllers

import play.api._
import play.api.mvc._

import form.PlayerForm._

object Player extends Controller with Front {

  def newForm = Action {
    Ok(views.html.player.newForm())
  }
}
