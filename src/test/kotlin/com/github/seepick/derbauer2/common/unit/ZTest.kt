package com.github.seepick.derbauer2.common.unit

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class ZTest : DescribeSpec({
    describe("misc") {
        it("toPrefixedString") {
            0.z.toPrefixedString() shouldBeEqual "0"
            1.z.toPrefixedString() shouldBeEqual "+1"
            1024.z.toPrefixedString() shouldBeEqual "+1k"
        }
    }
})
