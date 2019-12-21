package com.sg.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.sg.AkkaHttpClient
import com.sg.model.{DocumentResponse, NodeSetting}
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

class RootNodeRoute()(implicit ec: ExecutionContext) extends NodeRoute {

  val client = new AkkaHttpClient()

  def routes: Route = path("search") {
    parameter('q) { keywords =>
      onSuccess(fetchLeafResponse(keywords)) { res =>
        complete(res.asJson.toString())
      }
    }
  }

  private def fetchLeafResponse(keywords: String): Future[DocumentResponse] = {
    val nodeIds = Seq(1, 2)

    Future.sequence {
      NodeSetting.getOrigins(nodeIds).map { origin =>
        val url = s"$origin/search?q=$keywords"
        println(url)

        client.request(url).map { res =>
          println(res)
          decode[DocumentResponse](res).toOption
        }
      }
    }.map { responses =>
      val hits = responses.flatten.flatMap(_.hits).sortBy(_.id)
      DocumentResponse(hits)
    }
  }
}