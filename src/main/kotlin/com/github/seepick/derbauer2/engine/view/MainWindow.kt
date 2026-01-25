package com.github.seepick.derbauer2.engine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import com.github.seepick.derbauer2.engine.MatrixSize
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.PrintChar
import com.github.seepick.derbauer2.engine.Textmap
import com.github.seepick.derbauer2.engine.engineModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.module.Module
import kotlin.reflect.KClass

private val log = logger {}

private val padding = 10.dp
fun showMainWindow(
    title: String = "Main Window",
    mainModule: Module,
    initPage: KClass<out Page>,
    windowSize: MatrixSize,
) {
    application {
        KoinApplication(application = {
            modules(engineModule(initPage, windowSize), mainModule)
        }) {
            // FIXME adjust window size, correlate with textmap size
            val windowDpSize = DpSize(1040.dp + padding.times(2), 530.dp + padding.times(2))

            val state = rememberWindowState(size = windowDpSize)
            var tick by remember { mutableIntStateOf(0) }
            val page = getKoin().get<Page>(clazz = koinInject<CurrentPage>().page)
            log.debug { "tick #$tick / Current page: ${page::class.simpleName}" }
            val textmap = koinInject<Textmap>()

            MaterialTheme { // TODO custom theme
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
                            .border(padding, Color.fgColor)
                            .padding(padding)
                            .background(Color.bgColor)
                            .focusRequester(focusRequester)
                            .focusable()
                            .onPreviewKeyEvent { e -> // .onKeyEvent {  } ??
                                val key = e.toKeyPressed() ?: return@onPreviewKeyEvent false
                                page.onKeyPressed(key).also { isHandled ->
                                    if (isHandled) tick++
                                }
                            }
                    ) {
                        page.renderText(textmap)
                        MainTextArea(text = textmap.toFullString())
                        textmap.reset()
                    }
                }
            }
        }
    }
}

val Color.Companion.bgColor get() = Color(60, 50, 156, 0xFF)
val Color.Companion.fgColor get() = Color(122, 114, 212, 0xFF)


private fun KeyEvent.toKeyPressed(): KeyPressed? =
    if (type == KeyEventType.KeyDown) {
        when (key) {
            Key.Escape -> KeyPressed.Command.Escape
            Key.Enter -> KeyPressed.Command.Enter
            Key.Zero -> KeyPressed.Symbol(PrintChar.Numeric.Zero)
            Key.One -> KeyPressed.Symbol(PrintChar.Numeric.One)
            Key.Two -> KeyPressed.Symbol(PrintChar.Numeric.Two)
            else -> {
                log.debug { "Unhandled key: $key" }
                null
            }
        }
    } else {
        null
    }
