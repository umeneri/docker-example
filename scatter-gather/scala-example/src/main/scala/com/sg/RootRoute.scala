package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.sg.model.LeafResponse
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

import scala.concurrent.{ExecutionContext, Future}

class RootRoute()(implicit ec: ExecutionContext) extends Node {

  val urls: Seq[String] = Seq("http://localhost:5000/search?docs=0", "http://localhost:5001/search?docs=0")
  val client = new AkkaHttpClient()

  def routes: Route = pathPrefix("search") {
    pathEndOrSingleSlash {
      get {
        onSuccess(fetchLeafResponse) { res =>
          complete(res.asJson.toString())
        }
      }
    }
  }

  private def fetchLeafResponse: Future[LeafResponse] = {
    Future.sequence {
      urls.map { url =>
        client.request(url).map { res =>
          println(res)
          decode[LeafResponse](res).toOption
        }
      }
    }.map { responses =>
      val hits = responses.flatten.flatMap(_.hits)
      LeafResponse(hits)
    }
  }
}