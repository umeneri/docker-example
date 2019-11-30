package com.sg

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class RootRoute {

  import StatusCodes._

  def routes: Route = pathPrefix("search") {
    pathEndOrSingleSlash {
      get {
        complete(OK)
      }
    }
  }
  }
