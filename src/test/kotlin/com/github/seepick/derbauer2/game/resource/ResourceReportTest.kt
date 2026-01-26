package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.units
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual

class ResourceReportTest : DescribeSpec({
    describe("add") {
        it("When add two times same resource Then sums up") {
            val report = buildResourceReport {
                val gold = Gold(0.units)
                add(gold, 40.units)
                add(gold, 2.units)
            }
            report.lines.shouldBeSingleton().first().changeAmount shouldBeEqual 42.units
        }
    }
    describe("merge") {
        it("When has same Then sums up") {
            val gold = Gold(0.units)
            val report1 = buildResourceReport {
                add(gold, 40.units)
            }
            val report2 = buildResourceReport {
                add(gold, 2.units)
            }
            val merged = report1.merge(report2)

            merged.lines.shouldBeSingleton().first() shouldBeEqual ResourceReportLine(gold, 42.units)
        }
    }
})