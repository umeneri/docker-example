package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._

class LeafRoute extends FailFastCirceSupport {
  val documentRepository = DocumentRepository()

  def routes: Route = pathPrefix("search" / Segment) { docIdsParam =>
    pathEndOrSingleSlash {
      get {
        println(docIdsParam)

        val docIds = docIdsParam.split(",").toSeq.map(_.toInt)
        val docsJson = documentRepository.findAll(docIds)
//        println(docsJson.asJson.toString())
//        complete(docsJson.asJson.toString())
        complete("")
      }
    }
  }
}
