package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.viewer.MatrixSize
import com.github.seepick.derbauer2.viewer.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object DerBauer2 {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2" }
        showMainWindow(
            title = "Der Bauer 2",
            mainModule = gameModule(),
            initPage = HomePage::class,
            windowSize = MatrixSize(120, 30),
        )
    }
}
