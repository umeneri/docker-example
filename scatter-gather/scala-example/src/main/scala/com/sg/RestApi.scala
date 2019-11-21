package com.sg

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

trait RestApi {

  import StatusCodes._

  def routes: Route = eventsRoute

  def eventsRoute =
    pathPrefix("events") {
      pathEndOrSingleSlash {
        get {
          complete(OK)
        }
      }
    }
}
