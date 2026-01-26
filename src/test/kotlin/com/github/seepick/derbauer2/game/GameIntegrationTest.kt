package com.github.seepick.derbauer2.game

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
    }
}
