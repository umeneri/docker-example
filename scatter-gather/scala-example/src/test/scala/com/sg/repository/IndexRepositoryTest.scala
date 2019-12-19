package com.sg.repository

import org.scalatest.{FunSpec, Matchers}

class IndexRepositoryTest extends FunSpec with Matchers {

  describe("getDocumentMap") {
    it("should return document and node mapping") {
      val words = Seq("cat", "bard")
      val indexRepository = IndexRepository()
      val docs = indexRepository.getDocumentMap(words)

      docs shouldBe Map(1 -> Seq(2,3,5), 2 -> Seq(8))
    }
  }
}
