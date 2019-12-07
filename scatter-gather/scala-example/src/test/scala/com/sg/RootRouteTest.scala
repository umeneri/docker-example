package com.sg

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.parser._
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

class RootRouteTest extends FunSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with ArgumentMatchersSugar
{

  val client: HttpClient = ???

  describe("route") {

    it("should return search response") {
      val routes = new RootRoute(client).routes
      val request = Get("/search?q=cat%20dog")

      val expected =
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
          |          |
          |  ]
          |}""".stripMargin

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        parse(entityAs[String]).right.get.toString() should ===(expected)
      }
    }
  }
}
