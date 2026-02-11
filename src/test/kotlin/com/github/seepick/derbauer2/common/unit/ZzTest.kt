package com.github.seepick.derbauer2.common.unit

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class ZzTest : DescribeSpec({
    describe("misc") {
        it("toPlusString") {
            (-1024).zz.toSymboledString() shouldBeEqual "-1k"
            (-1).zz.toSymboledString() shouldBeEqual "-1"
            0.zz.toSymboledString() shouldBeEqual "0"
            1.zz.toSymboledString() shouldBeEqual "+1"
            1024.zz.toSymboledString() shouldBeEqual "+1k"
        }
    }
})
