package kcacup
package controllers

import play.api._
import play.api.mvc._

trait Front {

  protected val env = new Env(Play.unsafeApplication.configuration)

  /**
   * Retrieve the connected user username
   */
  def username(request: RequestHeader) =
    request.session.get("username")

  def user(request: RequestHeader) =
    username(request) flatMap env.userRepo.findOneByUsername

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = redirectIndex

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) =
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }

  def Public(f: => Option[models.User] => Request[AnyContent] => Result) =
    Action(request => f(user(request))(request))

  protected def redirectIndex =
    Results.Redirect(kcacup.controllers.routes.Main.index)
}
