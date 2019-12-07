package com.sg

import com.sg.model.Document
import org.scalatest.{FunSpec, Matchers}

class DocumentRepositoryTest extends FunSpec with Matchers {

  val docIds: Seq[Int] = Seq(0, 1)

  describe("find") {
    it("should find docs from ids") {
      val documentRepository = DocumentRepository()
      val docs = documentRepository.findAll(docIds)
      docs.head shouldBe Document(0, "dog0")
      docs(1) shouldBe Document(1, "dog1")
    }

    it("should return empty array from id which don't exists in directories") {
      val documentRepository = DocumentRepository()
      val docs = documentRepository.findAll(Seq(1,2,4))
      docs shouldBe Seq(Document(1, "dog1"))
    }
  }
}
