package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class LeafRoute {

  def routes: Route = pathPrefix("search" / Segment) { docIdsStr =>
    pathEndOrSingleSlash {
      get {
        println(docIdsStr)

        val docIds = docIdsStr.split(",").toSeq

        complete(docIds.toString())
      }
    }
  }
}
