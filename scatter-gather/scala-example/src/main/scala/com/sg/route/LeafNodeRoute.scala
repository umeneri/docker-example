package com.sg.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.sg.model.DocumentResponse
import com.sg.repository.DocumentRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

class LeafNodeRoute extends NodeRoute with FailFastCirceSupport {
  val documentRepository = DocumentRepository()

  def routes: Route =
    path("search") {
      parameter('docs) { docIdsParam =>
        val docIds = docIdsParam.split(",").toSeq.map(_.toInt)
        val docs = documentRepository.findAll(docIds)
        println(docs)
        val response = DocumentResponse(docs)
        complete(response.asJson)
      }
    }
}
