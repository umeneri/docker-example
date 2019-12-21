package com.sg.repository

import com.sg.model.NodeSetting
import org.scalatest.{FunSpec, Matchers}

class IndexRepositoryTest extends FunSpec with Matchers {

  describe("getDocumentMap") {
    it("should return document and node mapping") {
      val words = Seq("cat", "bard")
      val indexRepository = IndexRepository()
      val docs = indexRepository.getDocumentMap(words)

      docs shouldBe Map(
        NodeSetting(5001, "leaf1") -> Seq(5),
        NodeSetting(5002, "leaf2") -> Seq(2, 3, 8)
      )
    }
  }
}
