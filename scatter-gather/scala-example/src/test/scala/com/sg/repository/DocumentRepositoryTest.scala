package com.sg.repository

import com.sg.model.Document
import org.scalatest.{FunSpec, Matchers}

class DocumentRepositoryTest extends FunSpec with Matchers {

  describe("find") {
    it("should find docs from ids") {
      val documentRepository = DocumentRepository()
      val docs = documentRepository.findAll(Seq(0, 1))
      docs.head shouldBe Document(0, "dog 0")
      docs(1) shouldBe Document(1, "dog 1")
    }

    it("should return empty array from id which don't exists in directories") {
      val documentRepository = DocumentRepository()
      val docs = documentRepository.findAll(Seq(1,2,5))
      docs shouldBe Seq(Document(1, "dog 1"))
    }
  }
}
