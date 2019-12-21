package com.sg.route

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

class RootNodeRoute()(implicit ec: ExecutionContext) extends NodeRoute {

  val client = new AkkaHttpClient()
  val indexRepository = IndexRepository()

  def routes: Route = path("search") {
    parameter('q) { param =>
      onSuccess(fetchLeafResponse(param)) { res =>
        complete(res.asJson.toString())
      }
    }
  }

  private def fetchLeafResponse(keywords: String): Future[DocumentResponse] = {
    val words: Seq[String] = keywords.split(",").toSeq
    println(words)
    val nodeMapping = indexRepository.getDocumentMap(words).toSeq

    Future.sequence {
      nodeMapping.map { case (nodeSetting, docIds) =>
        println(nodeSetting, docIds)
        val url = s"${nodeSetting.localOrigin}/search?docs=${docIds.mkString(",")}"
        println(url)

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