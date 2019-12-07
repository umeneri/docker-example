package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.sg.model.LeafResponse
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._
import io.circe.generic.auto._

class LeafRoute extends FailFastCirceSupport {
  val documentRepository = DocumentRepository()

  def routes: Route = pathPrefix("search" / Segment) { docIdsParam =>
    pathEndOrSingleSlash {
      get {
        println(docIdsParam)

        val docIds = docIdsParam.split(",").toSeq.map(_.toInt)
        val docs = documentRepository.findAll(docIds)
        val response = LeafResponse(docs)
        complete(response.asJson)
      }
    }
  }
}
