package com.sg.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.sg.AkkaHttpClient
import com.sg.model.NodeSetting
import com.sg.repository.IndexRepository
import io.circe.parser._
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.Future

class RootNodeRouteTest extends FunSpec
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
          |      "body" : "dog 0"
          |    },
          |    {
          |      "id" : 1,
          |      "body" : "dog 1"
          |    },
          |    {
          |      "id" : 2,
          |      "body" : "cat 2"
          |    },
          |    {
          |      "id" : 3,
          |      "body" : "cat 3"
          |    },
          |    {
          |      "id" : 4,
          |      "body" : "cat 4"
          |    }
          |  ]
          |}""".stripMargin

      val json1 =
        """{
          |  "hits" : [
          |    {
          |      "id" : 0,
          |      "body" : "dog 0"
          |    },
          |    {
          |      "id" : 1,
          |      "body" : "dog 1"
          |    },
          |    {
          |      "id" : 4,
          |      "body" : "cat 4"
          |    }
          |  ]
          |}""".stripMargin

      val json2 =
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

      val routes: Route = new RootNodeRoute {
        override val client: AkkaHttpClient = mock[AkkaHttpClient]
        val urls: Seq[String] = Seq(
          s"${NodeSetting.get(1).localOrigin}/search?q=dog,cat",
          s"${NodeSetting.get(2).localOrigin}/search?q=dog,cat"
        )

        when(client.request(urls.head)).thenReturn(Future.successful(json1))
        when(client.request(urls(1))).thenReturn(Future.successful(json2))
      }.routes
      val request = Get("/search?q=dog,cat")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        val str = entityAs[String]
        parse(str).right.get.toString() should ===(json)
      }
    }
  }
}
