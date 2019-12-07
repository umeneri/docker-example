package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class RootRoute(client: HttpClient) {

  val url = ""

  def routes: Route = pathPrefix("search") {
    pathEndOrSingleSlash {
      get {
        val response = client.request(url)
        complete(response)
      }
    }
  }
  }
