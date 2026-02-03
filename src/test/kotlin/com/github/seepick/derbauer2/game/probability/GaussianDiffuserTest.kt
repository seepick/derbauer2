package com.github.seepick.derbauer2.game.probability

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GaussianDiffuserTest : StringSpec({
    "sdfa" {
        GaussianDiffuser(standardDeviation = 10, meanOffset = 5).diffuse(100) shouldBe 105
    }

})
