package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

class ResourceReportTest : DescribeSpec({
    describe("add") {
        it("When add two times same resource Then sums up") {
            val report = buildResourceChanges {
                val gold = Gold()
                add(gold, 40.z)
                add(gold, 2.z)
            }
            report.changes.shouldBeSingleton().first().change shouldBeEqual 42.zz
        }
    }
    describe("merge") {
        it("When has same Then sums up") {
            val gold = Gold()
            val report1 = buildResourceChanges {
                add(gold, 40.z)
            }
            val report2 = buildResourceChanges {
                add(gold, 2.z)
            }
            val merged = report1.merge(report2)

            merged.changes.shouldBeSingleton().first() shouldBeEqual ResourceChange(gold, 42.zz)
        }
    }
})
