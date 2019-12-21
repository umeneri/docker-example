package com.sg.node

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

import scala.collection.mutable
import scala.concurrent.Future

class RootNodeTest extends FunSpec
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

      val routes: Route = new RootNode {
        override val client: AkkaHttpClient = mock[AkkaHttpClient]
        override val indexRepository: IndexRepository = mock[IndexRepository]
        val urls: Seq[String] = Seq(
          s"${NodeSetting.get(1).localOrigin}/search?docs=0,1",
          s"${NodeSetting.get(2).localOrigin}/search?docs=2,3"
        )

        when(client.request(urls.head)).thenReturn(Future.successful(dogJson))
        when(client.request(urls(1))).thenReturn(Future.successful(catJson))
        when(indexRepository.getDocumentMap(any[Seq[String]])).thenReturn(
          Map(
            NodeSetting.get(1) -> Seq(0, 1),
            NodeSetting.get(2) -> Seq(2, 3),
          )
        )
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
