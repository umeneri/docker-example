package com.sg.repository

import org.scalatest.{FunSpec, Matchers}

class IndexRepositoryTest extends FunSpec with Matchers {

  describe("getDocumentId") {
    it("should return document ids") {
      val words = Seq("cat", "bard")
      val indexRepository = IndexRepository()
      val docIds = indexRepository.getDocumentIds(words)

      docIds shouldBe Seq(2, 3, 4, 5, 8)
    }
  }
}
