package com.github.seepick.derbauer2.game.testInfra.uitest

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.awt.Robot
import java.awt.Window

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
