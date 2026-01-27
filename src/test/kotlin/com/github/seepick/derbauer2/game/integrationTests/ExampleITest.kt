package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.textengine.Beeper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.verify
import org.koin.test.KoinTest
import org.koin.test.get

class ExampleITest : KoinTest, FunSpec() {
    init {
        gameKoinExtension()
        test("skip next turn brings you to the report page") {
            Given {} When {
                input(KeyInput.Enter)
            } Then {
                page.shouldBeInstanceOf<ReportPage>()
            }
        }
        test("Given no gold When buy resource Then beep no gold") {
            Given {
                setOwned<Gold>(0.z)
            } When {
                selectPrompt("trade")
                selectPrompt("buy 1 🍖")
            } Then {
                verify(exactly = 1) {
                    get<Beeper>().beep(match { it.contains("Insufficient resources") })
                }
            }
        }
        test("citizens pay taxes") {
            val citizenCount = 100.z
            Given {
                setOwned<Gold>(0.z)
                setOwned<House>(20.z)
                changeOwned<Land>(20.z)
                setOwned<Citizen>(citizenCount)
            } When {
                input(KeyInput.Enter)
            } Then {
                val expectedTax = citizenCount * Mechanics.citizenTax
                shouldOwn<Gold>(expectedTax)
            }
        }
    }
}
