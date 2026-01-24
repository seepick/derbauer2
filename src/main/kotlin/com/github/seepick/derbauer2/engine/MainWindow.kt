package com.github.seepick.derbauer2.engine

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.seepick.derbauer2.game.MainView

class MainWindow(
    val title: String = "Main Window",
) {
    fun show() {
        application {
            val state = rememberWindowState(size = DpSize(900.dp, 600.dp))
            Window(
                title = title,
                onCloseRequest = ::exitApplication,
                resizable = false,
                state = state
            ) {
                MainView()
            }
        }
    }
}