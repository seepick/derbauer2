package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.textengine.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object DerBauer2 {
    private val log = logger {}
    val initPageClass = HomePage::class

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2 (args: ${args.toList()})" }
        showMainWindow(
            title = "DerBauer2",
            mainModule = gameModule(),
            initPageClass = initPageClass,
        ) { user -> user.initAssets() }
    }
}
