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
  }

}
