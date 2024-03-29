package kcacup
package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Results._

trait Front {

  protected val env = new Env(Play.unsafeApplication.configuration)

  /**
   * Retrieve the connected user username
   */
  def getUsername(request: RequestHeader) =
    request.session.get("username")

  def getUser(request: RequestHeader) =
    getUsername(request) flatMap env.userRepo.findOneByUsername

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = redirectIndex

  /**
   * Action for authenticated users.
   */
  def Authenticated(f: models.User => Request[AnyContent] => Result) =
    Action(request => getUser(request) some {
      f(_)(request)
    } none {
      Unauthorized(views.html.defaultpages.unauthorized())
    })

  def Public(f: Option[models.User] => Request[AnyContent] => Result) =
    Action(request => f(getUser(request))(request))

  protected def redirectIndex =
    Results.Redirect(kcacup.controllers.routes.Main.index)
}
