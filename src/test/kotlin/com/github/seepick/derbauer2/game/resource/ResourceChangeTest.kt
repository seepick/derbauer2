package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.z
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldBeEmpty

class ResourceChangeTest : DescribeSpec({
    describe("toTextmapRendering") {
        it("Given nothing Then empty") {
            buildResourceChanges {}.toTextmapRendering().shouldBeEmpty()
        }
        it("Given single resource Then rendered") {
            buildResourceChanges {
                add(Gold::class, 2.z)
            }.toTextmapRendering() shouldBeEqual "2 ${Gold.Data.emojiOrNull}"
        }
        it("Given two resources Then both rendered") {
            buildResourceChanges {
                add(Gold::class, 2.z)
                add(Food::class, 3.z)
            }.toTextmapRendering() shouldBeEqual "2 ${Gold.Data.emojiOrNull}, 3 ${Food.Data.emojiOrNull}"
        }
    }
})
