package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.engine.view.showMainWindow
import com.github.seepick.derbauer2.game.logic.gameLogicModule
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.gameViewModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.dsl.module

object DerBauer2 {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2" }
        showMainWindow(
            title = "Der Bauer 2",
            mainModule = gameModule(),
            initPage = HomePage::class,
        )
    }
}

fun gameModule() = module {
    includes(gameLogicModule(), gameViewModule())
}
