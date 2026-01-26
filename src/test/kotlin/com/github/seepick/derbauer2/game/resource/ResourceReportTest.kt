package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.logic.zp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

class ResourceReportTest : DescribeSpec({
    describe("add") {
        it("When add two times same resource Then sums up") {
            val report = buildResourceReport {
                val gold = Gold(0.zp)
                add(gold, 40.zp)
                add(gold, 2.zp)
            }
            report.lines.shouldBeSingleton().first().changeAmount shouldBeEqual 42.z
        }
    }
    describe("merge") {
        it("When has same Then sums up") {
            val gold = Gold(0.zp)
            val report1 = buildResourceReport {
                add(gold, 40.zp)
            }
            val report2 = buildResourceReport {
                add(gold, 2.zp)
            }
            val merged = report1.merge(report2)

            merged.lines.shouldBeSingleton().first() shouldBeEqual ResourceReportLine(gold, 42.z)
        }
    }
})