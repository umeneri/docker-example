package com.sg.node

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.sg.AkkaHttpClient
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
          |      "id" : 2,
          |      "body" : "cat 2"
          |    },
          |    {
          |      "id" : 3,
          |      "body" : "cat 3"
          |    },
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

      val dogJson =
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

      val catJson =
        """{
          |  "hits" : [
          |    {
          |      "id" : 2,
          |      "body" : "cat 2"
          |    },
          |    {
          |      "id" : 3,
          |      "body" : "cat 3"
          |    }
          |  ]
          |}""".stripMargin

      val routes: Route = new RootRoute {
        override val client: AkkaHttpClient = mock[AkkaHttpClient]
        val urls: Seq[String] = Seq("http://localhost:5000/search?docs=0,1", "http://localhost:5001/search?docs=2,3")

        when(client.request(urls.head)).thenReturn(Future.successful(dogJson))
        when(client.request(urls(1))).thenReturn(Future.successful(catJson))
      }.routes
      val request = Get("/search?q=cat,dog")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        val str = entityAs[String]
        parse(str).right.get.toString() should ===(json)
      }
    }
  }
}
