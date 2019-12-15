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

  describe("rootRoute") {

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
        override val client: AkkaHttpClient = mock[AkkaHttpClient]
        when(client.request(urls.head)).thenReturn(Future.successful(""))
        when(client.request(urls(1))).thenReturn(Future.successful(""))
      }.routes
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
