package com.sg

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.sg.model.LeafResponse
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

class RootRoute()(implicit ec: ExecutionContext) extends Node {

  val urls: Seq[String] = Seq("http://localhost:9001/search", "http://localhost:9002/search")
  val client = new AkkaHttpClient()

  def routes: Route = pathPrefix("search") {
    pathEndOrSingleSlash {
      get {
        val response = Future.sequence {
          urls.map { url =>
            client.request(url).map { res =>
              println(res)
              getLeafResponse(res)
            }
          }
        }.map { responses =>
          val hits = responses.flatten.flatMap(_.hits)
          LeafResponse(hits)
        }

        onSuccess(response) { res =>
          complete(res.asJson.toString())
        }
      }
    }
  }

  def getLeafResponse(res: String): Option[LeafResponse] = {
    decode[LeafResponse](res).toOption
  }
}