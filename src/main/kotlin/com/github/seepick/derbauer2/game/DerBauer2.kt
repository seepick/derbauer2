package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.AppProperties
import com.github.seepick.derbauer2.game.core.DerBauer2SysProp
import com.github.seepick.derbauer2.game.view.`derbauer2 🏰`
import com.github.seepick.derbauer2.textengine.compose.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.Koin
import kotlin.reflect.KClass

object DerBauer2 {
    private val log = logger {}
    val isDevMode = System.getProperty(DerBauer2SysProp.DEV_MODE.key) != null

    init {
        log.info { "DerBauer2 devMode=${isDevMode}" }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting DerBauer2 (args=${args.toList()})" }
        startApp(
            isDevMode = isDevMode,
            version = AppProperties.instance.version,
            prefStorageFqn = DerBauer2::class,
        )
    }
}

fun startApp(
    isDevMode: Boolean,
    version: String,
    prefStorageFqn: KClass<*>,
    postInit: (Koin) -> Unit = {},
) {
    val appTitle = "DerBauer2 ${Emoji.`derbauer2 🏰`}"
    val devModeSuffix = if (isDevMode) " ⛑️ Dev Mode ⛑️" else ""

    showMainWindow(
        title = "$appTitle v${version}$devModeSuffix",
        mainModule = gameModule(prefStorageFqn),
        initState = { koin ->
            koin.initGame()
            postInit(koin)
        },
    )
}
