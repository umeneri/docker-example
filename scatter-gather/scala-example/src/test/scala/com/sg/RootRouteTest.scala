package com.sg

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.parser._
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.Future

class RootRouteTest extends FunSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with ArgumentMatchersSugar {

  describe("route") {

    it("should return search response") {
      val json =
        """{
          |  "hits" : [
          |    {
          |      "id" : 0,
          |      "body" : "dog0"
          |    },
          |    {
          |      "id" : 1,
          |      "body" : "dog1"
          |    },
          |    {
          |      "id" : 0,
          |      "body" : "cat0"
          |    },
          |    {
          |      "id" : 1,
          |      "body" : "cat1"
          |    }
          |  ]
          |}""".stripMargin

      val routes: Route = new RootRoute {
        override val client: AkkaHttpClient = new AkkaHttpClient {
          override def request(url: String): Future[String] =
            Future.successful(json)
        }
      }.routes
      //      val routes = new RootRoute.routes
      val request = Get("/search?q=cat%20dog")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        val str = entityAs[String]
        println(str)
        parse(str).right.get.toString() should ===(json)
      }
    }
  }
}
