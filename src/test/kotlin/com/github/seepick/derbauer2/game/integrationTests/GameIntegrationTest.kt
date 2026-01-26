package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.shouldContainChange
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.Beeper
import com.github.seepick.derbauer2.textengine.MatrixSize
import com.github.seepick.derbauer2.textengine.textengineModule
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.koin.test.KoinTest
import org.koin.test.inject

object DummyBeeper : Beeper {
    override fun beep(reason: String) {
        // do nothing
    }
}

class GameIntegrationTest : KoinTest, FunSpec() {
    init {
        extension(
            KoinExtension(
                listOf(
                    textengineModule(HomePage::class, MatrixSize(1, 1)),
                    gameModule()
                )
            ) {
                mockk<Beeper> {
                    every { beep(any()) } just runs
                }
            }
        )

        test("simply hit next turn right away") {
            val turner by inject<Turner>()
            val report = turner.collectAndExecuteNextTurnReport()
            report.turn shouldBeEqual 1
        }

        test("citizens pay taxes") {
            val turner by inject<Turner>()
            val user by inject<User>()
            val citizenResource = user.add(Citizen(100.z))
            val goldResource = user.add(Gold(0.z))

            val report = turner.collectAndExecuteNextTurnReport()

            val expectedTax = (citizenResource.owned.value * Mechanics.citizenTaxPercentage).toLong().z
            goldResource.owned shouldBeEqual expectedTax
            report.resourceReportLines.shouldContainChange(goldResource to expectedTax.asZ)
        }
    }
}