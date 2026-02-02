package com.github.seepick.derbauer2.game.testInfra.uitest

import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.initGame
import com.github.seepick.derbauer2.game.view.textengineModule
import com.github.seepick.derbauer2.textengine.compose.MainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import java.awt.Robot
import java.awt.Window

fun uitestModule() = module {
    // TODO enable test beeper after confirmed it beeps without
//    single { IgnoringBeeper } bind Beeper::class
}

private val log = logger {}

/** ensure OS mouse pointer is inside the test window so synthesized events are delivered */
fun moveRealMouseOverWindow() {
    log.debug { "capture host OS mouse and put it above displayed window ;)" }
    val robot = Robot()
    val window = Window.getWindows().firstOrNull { it.isVisible } ?: error("No visible window found for the test")
    val centerX = window.locationOnScreen.x + window.width / 2
    val centerY = window.locationOnScreen.y + window.height / 2
    robot.mouseMove(centerX, centerY)
    Thread.sleep(50) // small pause to let the OS settle the pointer
}

fun ComposeTest.uiTest(testCode: () -> Unit) {
    ui.setContent {
        KoinApplication(application = {
            modules(textengineModule(), gameModule(this@uiTest::class), uitestModule())
        }) {
            MainWindow(title = "TestBauer2", { it.initGame() }, {})
        }
    }

    moveRealMouseOverWindow()
    logGameText()
    testCode()
}
