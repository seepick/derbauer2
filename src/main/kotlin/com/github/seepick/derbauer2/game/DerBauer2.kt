package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.core.AppProperties
import com.github.seepick.derbauer2.game.core.Emoji
import com.github.seepick.derbauer2.game.core.emoji
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.textengine.compose.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object DerBauer2 {
    private val log = logger {}
    val isDevMode = System.getProperty("derbauer2.devMode") != null
    val initPageClass = HomePage::class
    val emoji = "🏰".emoji

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2 - devMode=${isDevMode} (args: ${args.toList()})" }

        val appTitle = "DerBauer2 ${Emoji.derbauer2}"
        val devModeSuffix = if (isDevMode) " ⛑️ Dev Mode ⛑️" else ""
        showMainWindow(
            title = "$appTitle v${AppProperties.instance.version}$devModeSuffix",
            mainModule = gameModule(DerBauer2::class),
        ) { koin -> koin.initGame() }
    }
}

val Emoji.Companion.derbauer2 get() = DerBauer2.emoji
