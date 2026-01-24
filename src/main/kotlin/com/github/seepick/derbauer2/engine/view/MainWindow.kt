package com.github.seepick.derbauer2.engine.view

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.PrintChar
import com.github.seepick.derbauer2.engine.engineModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.module.Module
import kotlin.reflect.KClass

private val log = logger {}

private val windowSize = DpSize(900.dp, 600.dp)

fun showMainWindow(
    title: String = "Main Window",
    mainModule: Module,
    initPage: KClass<out Page>,
) {
    application {
        KoinApplication(application = {
            modules(engineModule(initPage), mainModule)
        }) {
            val state = rememberWindowState(size = windowSize)
            var tick by remember { mutableIntStateOf(0) }
            val page = getKoin().get<Page>(clazz = koinInject<CurrentPage>().page)
            log.debug { "tick #$tick / Current page: ${page::class.simpleName}" }

            Window(
                title = title,
                onCloseRequest = ::exitApplication,
                resizable = false,
                state = state,
            ) {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester)
                        .focusable()
                        .onPreviewKeyEvent { e ->
                            val key = e.toKeyPressed() ?: return@onPreviewKeyEvent false
                            page.onKeyPressed(key).also { isHandled ->
                                if (isHandled) tick++
                            }
                        }
                ) {
                    MainTextArea(text = page.renderText())
                }
            }
        }
    }
}

private fun KeyEvent.toKeyPressed(): KeyPressed? =
    if (type == KeyEventType.KeyDown) {
        when (key) {
            Key.Escape -> KeyPressed.Escape
            Key.Enter -> KeyPressed.Enter
            Key.One -> KeyPressed.Printable(PrintChar.Numeric.One)
            Key.Two -> KeyPressed.Printable(PrintChar.Numeric.Two)
            else -> {
                log.debug { "Unhandled key: $key" }
                null
            }
        }
    } else {
        null
    }
