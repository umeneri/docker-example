package com.sg.node

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.sg.AkkaHttpClient
import com.sg.model.DocumentResponse
import com.sg.repository.IndexRepository
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

class RootRoute()(implicit ec: ExecutionContext) extends Node {

  val client = new AkkaHttpClient()

  def routes: Route = path("search") {
    parameter('q) { param =>
      onSuccess(fetchLeafResponse(param)) { res =>
        complete(res.asJson.toString())
      }
    }
  }

  private def fetchLeafResponse(keywords: String): Future[DocumentResponse] = {
    val words = keywords.split(",")
    val nodeMapping = IndexRepository().getDocumentMap(words).toSeq

    Future.sequence {
      nodeMapping.map { case (nodeId, docIds) =>
        println(nodeId, docIds)
        val url = s"http://localhost:500$nodeId/search?docs=${docIds.mkString(",")}"

        client.request(url).map { res =>
          println(res)
          decode[DocumentResponse](res).toOption
        }
      }
    }.map { responses =>
      val hits = responses.flatten.flatMap(_.hits)
      DocumentResponse(hits)
    }
  }
}