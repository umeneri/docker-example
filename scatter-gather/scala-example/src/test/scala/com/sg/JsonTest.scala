package com.sg

import com.sg.model.{Document, DocumentResponse}
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalatest.{FunSpec, Matchers}
import io.circe.parser._

class JsonTest extends FunSpec with Matchers {
  describe("json encode") {
    it("should encode") {
      val leafResponse = DocumentResponse(hits = List(Document(id = 1, body = "cat")))
      val expected =
        """{
          |  "hits" : [
          |    {
          |      "id" : 1,
          |      "body" : "cat"
          |    }
          |  ]
          |}""".stripMargin

      leafResponse.asJson.toString() shouldBe expected
    }

    it("should decode") {
      val expected = DocumentResponse(hits = List(Document(id = 1, body = "cat")))
      val json =
        """{
          |  "hits" : [
          |    {
          |      "id" : 1,
          |      "body" : "cat"
          |    }
          |  ]
          |}""".stripMargin

      decode[DocumentResponse](json).right.get shouldBe expected
    }
  }
}
