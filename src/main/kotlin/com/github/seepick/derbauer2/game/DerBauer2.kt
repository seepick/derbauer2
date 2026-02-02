package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.core.AppProperties
import com.github.seepick.derbauer2.game.core.Emoji
import com.github.seepick.derbauer2.game.core.emoji
import com.github.seepick.derbauer2.textengine.compose.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.Koin
import kotlin.reflect.KClass

object DerBauer2 {
    private val log = logger {}
    val isDevMode = System.getProperty("derbauer2.devMode") != null

    init {
        log.info { "DerBauer2 devMode=${isDevMode}" }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting DerBauer2 (args=${args.toList()})" }
        startApp(
            isDevMode = isDevMode,
            version = AppProperties.instance.version,
            prefStatePath = DerBauer2::class,
        )
    }
}

private val appEmoji = "🏰".emoji
val Emoji.Companion.derbauer2 get() = appEmoji

fun startApp(
    isDevMode: Boolean,
    version: String,
    prefStatePath: KClass<*>,
    postInit: (Koin) -> Unit = {},
) {
    val appTitle = "DerBauer2 ${Emoji.derbauer2}"
    val devModeSuffix = if (isDevMode) " ⛑️ Dev Mode ⛑️" else ""

    showMainWindow(
        title = "$appTitle v${version}$devModeSuffix",
        mainModule = gameModule(prefStatePath),
    ) { koin ->
        koin.initGame()
        postInit(koin)
    }
}
