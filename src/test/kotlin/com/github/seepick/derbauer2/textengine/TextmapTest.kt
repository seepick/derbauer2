package com.github.seepick.derbauer2.textengine

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TextmapTest : DescribeSpec( {
    describe("full string") {
        it("single char") {
            Textmap(1, 1).printLine("a").toFullString() shouldBeEqual "a"
        }
        // reset
    }
})