package kcacup
package controllers

import play.api._
import play.api.mvc._

trait Front {

  protected def redirectIndex =
    Results.Redirect(kcacup.controllers.routes.EventAdmin.index)
}
