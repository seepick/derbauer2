package com.github.seepick.derbauer2.textengine

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.textengine.audio.MusicButton
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.module.Module

private val log = logger {}
private val outerBorder = 10.dp
private val innerMargin = 5.dp
val mainWindowMatrixSize = MatrixSize(rows = 25, cols = 80)
private val mainContentWidth = 10.85.dp * mainWindowMatrixSize.cols
private val mainContentHeight = 22.2.dp * mainWindowMatrixSize.rows

private fun calcWinSize(): DpSize {
    val borderAndMarginGap = outerBorder.times(2) + innerMargin.times(2)
    return DpSize(
        width = mainContentWidth + borderAndMarginGap,
        height = mainContentHeight + borderAndMarginGap, // + 100.dp,
    )
}

fun textengineModule() = textengineModule(DerBauer2.initPageClass, mainWindowMatrixSize)

// TODO let it be refactored
@Suppress("LongMethod", "MagicNumber", "CognitiveComplexMethod")
fun showMainWindow(
    title: String = "Main Window",
    mainModule: Module,
    initState: (User) -> Unit,
) {
    application {
        KoinApplication(application = {
            allowOverride(false)
            createEagerInstances()
            modules(textengineModule(), mainModule)
        }) {
            val windowDpSize = calcWinSize()
            val state = rememberWindowState(size = windowDpSize)
            var tick by remember { mutableIntStateOf(0) }
            val currentPage = koinInject<CurrentPage>()
            val page = getKoin().get<Page>(clazz = currentPage.pageClass)
            tick.toString() // HACK to trigger recomposition, otherwise tick changes are not observed
            log.trace { "UI render tick #$tick" }
            val textmap = koinInject<Textmap>()

            val density = LocalDensity.current
            val leftBarWidth = 40.dp
            val cursorLeftThreshold = with(density) { leftBarWidth.toPx() }
            var isCursorNearLeft by remember { mutableStateOf(false) }
            val leftBarAnimationDuration = 200
            val leftBarOffset by animateDpAsState(
                targetValue = if (isCursorNearLeft) 0.dp else -leftBarWidth,
                animationSpec = tween(durationMillis = leftBarAnimationDuration)
            )
            val leftBarTargetAlpha = 0.8f
            val leftBarAlpha by animateFloatAsState(
                targetValue = if (isCursorNearLeft) leftBarTargetAlpha else 0.0f,
                animationSpec = tween(durationMillis = leftBarAnimationDuration)
            )

            MaterialTheme {
                // state hack: ensure initialization runs only once during composition
                var initialized by remember { mutableIntStateOf(0) }
                if (initialized == 0) {
                    initialized = 1
                    log.debug { "Compose is going to initialize state (initialized=$initialized)" }
                    val user = koinInject<User>()
                    initState(user)
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
                                    if (isHandled) {
                                        tick++ // trigger re-composition
                                    }
                                }
                            }
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        val pos = event.changes.firstOrNull()?.position ?: continue
                                        val near = pos.x <= cursorLeftThreshold
                                        if (near != isCursorNearLeft) {
                                            isCursorNearLeft = near
                                        }
                                    }
                                }
                            }
                    ) {
                        page.invalidate()
                        page.renderText(textmap)
                        MainTextArea(
                            text = textmap.toFullString(),
                        )
                        textmap.reset()


                        // left edge sliding bar
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .offset(x = leftBarOffset)
                                    .width(leftBarWidth)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.fgColor.copy(alpha = leftBarAlpha))
                            ) {
                                MusicButton(autoPlayMusic = false)
                            }
                        }
                    }
                }
            }
        }
    }
}
