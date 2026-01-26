package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.turn.Turner
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.equals.shouldBeEqual
import org.koin.test.KoinTest
import org.koin.test.inject

class GameIntegrationTest : KoinTest, FunSpec() {
    init {
//        textengineModule(HomePage::class, MatrixSize(1, 1)),
        extension(KoinExtension(gameModule()) {
//            mockk<UserService>() }
        })

        test("simply hit next turn right away") {
            val turner by inject<Turner>()
            val report = turner.collectAndExecuteNextTurnReport()
            report.turn shouldBeEqual 1
        }
        test("citizens pay taxes") {
            val turner by inject<Turner>()
            val user by inject<User>()
            val citizenResource = user.add(Citizen(100.units))
            val goldResource = user.add(Gold(0.units))

            val report = turner.collectAndExecuteNextTurnReport()

            val expectedTax = (citizenResource.owned.single * Mechanics.citizenTaxPercentage).toLong().units
            goldResource.owned shouldBeEqual expectedTax
            report.resourceReportLines.shouldContainChange(goldResource to expectedTax)
        }
    }
}
