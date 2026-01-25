package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.MatrixSize
import com.github.seepick.derbauer2.textengine.textengineModule
import io.kotest.core.spec.style.StringSpec
import org.koin.core.context.startKoin

class GameIntegrationTest : StringSpec( {
    "game with textengine starts" {
        val koin = startKoin {
            modules(
                textengineModule(HomePage::class, MatrixSize(1, 1)),
                gameModule(),
            )
        }.koin
    }

    "game starts without textengine" {
        val koin = startKoin {
            modules(gameModule()) // TODO actually without view module...
        }.koin
        val turner = koin.get<Turner>()
        turner.collectAndExecuteNextTurnReport()
    }
})

