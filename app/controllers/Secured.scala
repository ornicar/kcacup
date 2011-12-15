package kcacup
package controllers

import play.api._
import play.api.mvc._
import play.api.data._

/**
 * Provide security features
 */
trait Secured extends Front {

  /**
   * Retrieve the connected user username
   */
  private def username(request: RequestHeader) =
    request.session.get("username")

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
}
