package com.sg.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.parser._
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

class LeafNodeRouteTest extends FunSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with ArgumentMatchersSugar {
  {

    describe("leafRoute") {
      it("should get empty document") {
        val LeafRoute = new LeafNodeRoute()
        lazy val routes: Route = LeafRoute.routes
        val request = Get("/search?docs=8")

        val expected =
          """{
            |  "hits" : [
            |  ]
            |}""".stripMargin

        request ~> routes ~> check {
          status should ===(StatusCodes.OK)
          parse(entityAs[String]).right.get.toString() should ===(expected)
        }
      }

      it("should get matching one document") {
        val LeafRoute = new LeafNodeRoute()
        lazy val routes: Route = LeafRoute.routes
        val request = Get("/search?docs=1")

        val expected =
          """{
            |  "hits" : [
            |    {
            |      "id" : 1,
            |      "body" : "dog 1"
            |    }
            |  ]
            |}""".stripMargin

        request ~> routes ~> check {
          status should ===(StatusCodes.OK)
          parse(entityAs[String]).right.get.toString() should ===(expected)
        }
      }

      it("should get matching documents") {
        val LeafRoute = new LeafNodeRoute()
        lazy val routes: Route = LeafRoute.routes
        val request = Get("/search?docs=0,1")

        val expected =
          """{
            |  "hits" : [
            |    {
            |      "id" : 0,
            |      "body" : "dog 0"
            |    },
            |    {
            |      "id" : 1,
            |      "body" : "dog 1"
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

