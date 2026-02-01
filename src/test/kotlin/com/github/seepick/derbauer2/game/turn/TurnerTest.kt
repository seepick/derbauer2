package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.happening.happenings.DefaultHappeningDescriptorRepo
import com.github.seepick.derbauer2.game.probability.ProbabilitiesImpl
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs

class TurnerTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: Turner
    lateinit var reports: ReportIntelligence
    lateinit var probabilities: ProbabilitiesImpl
    beforeTest {
        user = User()
        reports = ReportIntelligence()
        probabilities = ProbabilitiesImpl()
        val happeningTurner = HappeningTurner(user, probabilities, DefaultHappeningDescriptorRepo)
        happeningTurner.registerProbabilities()
        turner = Turner(
            happeningTurner = happeningTurner,
            featureTurner = FeatureTurner(user),
            reports = reports,
            user = user,
            resourceTurnSteps = emptyList(),
        )
    }

    describe("misc") {
        it("turn changed") {
            val old = turner.turn
            turner.collectAndExecuteNextTurnReport()

            turner.turn shouldBeEqual old + 1
        }
        it("intelligence updated") {
            val report = turner.collectAndExecuteNextTurnReport()

            reports.last() shouldBeSameInstanceAs report
        }
    }
    describe("game over") {
        it("Given no entity Then game cant be over") {
            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual false
        }
        it("Given >0 citizens When take turn Then game is not over") {
            val house = House()
            user.enableAndSet(Land(), house.landUse)
            user.enableAndSet(house, 1.z)
            user.enableAndSet(Citizen(), 1.z)

            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual false
        }
        it("Given no citizens When take turn Then game is over") {
            user.enableAndSet(Citizen(), 0.z)

            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual true
        }
    }
})
