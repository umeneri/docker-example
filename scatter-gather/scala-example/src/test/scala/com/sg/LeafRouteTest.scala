package com.sg

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.parser._
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

class LeafRouteTest extends FunSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with ArgumentMatchersSugar {
  {

    describe("testRoutes") {
      it("should get matching documents") {
        val LeafRoute = new LeafRoute()
        lazy val routes: Route = LeafRoute.routes
        val request = Get("/search/1")

        val expected =
          """{
            |  "hits" : [
            |    {
            |      "id" : 1,
            |      "body" : "dog1"
            |    }
            |  ]
            |}""".stripMargin

        request ~> routes ~> check {
          status should ===(StatusCodes.OK)
          parse(entityAs[String]).right.get.toString() should ===(expected)
        }
      }
    }
  }
}

