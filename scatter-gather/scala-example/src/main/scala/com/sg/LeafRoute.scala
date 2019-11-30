package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class LeafRoute {
  val documentRepository = DocumentRepository()

  def routes: Route = pathPrefix("search" / Segment) { docIdsStr =>
    pathEndOrSingleSlash {
      get {
        println(docIdsStr)

        val docIds = docIdsStr.split(",").toSeq.map(_.toInt)
        val docsJson = documentRepository.findAll(docIds)

        complete(docsJson.toString())
      }
    }
  }
}
