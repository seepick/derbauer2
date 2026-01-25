package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.viewer.MatrixSize
import com.github.seepick.derbauer2.viewer.viewerModule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.koin.core.context.GlobalContext.startKoin

class ModuleTest : StringSpec( {
    "koin starts" {
        val koin = startKoin {
            modules(
                viewerModule(HomePage::class, MatrixSize(1, 1)),
                gameModule(),
            )
        }.koin
        koin.get<User>() shouldBeSameInstanceAs koin.get<Game>().user
    }
})