package com.sg.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.sg.model.DocumentResponse
import com.sg.repository.{DocumentRepository, IndexRepository}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

class LeafNodeRoute extends NodeRoute with FailFastCirceSupport {
  val documentRepository = DocumentRepository()
  val indexRepository = IndexRepository()

  def routes: Route =
    path("search") {
      parameter('q) { param =>
        val words = param.split(",").toSeq
        val docIds = indexRepository.getDocumentIds(words)
        val docs = documentRepository.findAll(docIds)
        val response = DocumentResponse(docs)
        complete(response.asJson)
      }
    }
}
