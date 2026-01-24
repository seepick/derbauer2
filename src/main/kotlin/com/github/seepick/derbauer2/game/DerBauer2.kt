package com.github.seepick.derbauer2.game

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.github.seepick.derbauer2.engine.MainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object DerBauer2 {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2" }

        MainWindow(
            title = "Der Bauer 2",
        ).show()
    }
}

@Composable
fun MainView(
//    viewModel: MainViewModel = koinViewModel(),
//    sharedModel: SharedModel = koinInject(),
) {
    TextButton(
        onClick = { println("click") }
    ) {
        Text("Hello World!")
    }
}
