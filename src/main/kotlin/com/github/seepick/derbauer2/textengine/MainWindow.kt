package com.github.seepick.derbauer2.textengine

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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.seepick.derbauer2.game.logic.Game
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.module.Module
import kotlin.reflect.KClass

private val log = logger {}
private val outerBorder = 10.dp
private val innerMargin = 5.dp
private val mainFontSize = 18.sp
private val windowSize = MatrixSize(rows = 25, cols = 80)
private val mainContentWidth = (11.2).dp * windowSize.cols
private val mainContentHeight = (22.2).dp * windowSize.rows

private fun calcWinSize(): DpSize {
    // FIXME adjust window size, correlate with textmap size
    val borderAndMarginGap = outerBorder.times(2) + innerMargin.times(2)
    return DpSize(
        width = mainContentWidth + borderAndMarginGap,
        height = mainContentHeight + borderAndMarginGap,
    )
}

fun showMainWindow(
    title: String = "Main Window",
    mainModule: Module,
    initPage: KClass<out Page>,
    initState: (Game) -> Unit,
) {
    application {
        KoinApplication(application = {
            allowOverride(false)
            createEagerInstances()
            modules(textengineModule(initPage, windowSize), mainModule)
        }) {

            val windowDpSize = calcWinSize()
            val state = rememberWindowState(size = windowDpSize)
            var tick by remember { mutableIntStateOf(0) }
            val page = getKoin().get<Page>(clazz = koinInject<CurrentPage>().page)
            log.debug { "tick #$tick / Current page: ${page::class.simpleName}" }
            val textmap = koinInject<Textmap>()

            MaterialTheme {
                // state hack: ensure initialization runs only once during composition
                var initialized by remember { mutableIntStateOf(0) }
                if (initialized == 0) {
                    val game = koinInject<Game>()
                    initState(game)
                    initialized = 1
                }

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
                            .border(outerBorder, Color.fgColor)
                            .padding(outerBorder)
                            .background(Color.bgColor)
                            .padding(innerMargin)
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
                        MainTextArea(
                            text = textmap.toFullString(),
                            fontSize = mainFontSize,
                        )
                        textmap.reset()
                    }
                }
            }
        }
    }
}

private fun KeyEvent.toKeyPressed(): KeyPressed? =
    if (type == KeyEventType.KeyDown) {
        when (key) {
            Key.Escape -> KeyPressed.Command.Escape
            Key.Enter -> KeyPressed.Command.Enter
            Key.Spacebar -> KeyPressed.Command.Space
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
