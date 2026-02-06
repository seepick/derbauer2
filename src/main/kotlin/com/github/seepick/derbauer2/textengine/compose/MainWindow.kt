package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.seepick.derbauer2.game.view.textengineModule
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.bgColor
import com.github.seepick.derbauer2.textengine.fgColor
import com.github.seepick.derbauer2.textengine.keyboard.toKeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.Koin
import org.koin.core.module.Module

private val log = logger {}

fun showMainWindow(
    title: String = "Main Window",
    mainModule: Module,
    initState: (Koin) -> Unit = {},
) {
    application {
        KoinApplication(application = {
            modules(textengineModule(), mainModule)
        }) {
            MainWindow(
                title = title,
                initState = initState,
                onClose = ::exitApplication,
            )
        }
    }
}

@Suppress("FunctionName", "DestructuringDeclarationWithTooManyEntries")
@Composable
fun MainWindow(
    title: String,
    initState: (Koin) -> Unit,
    onClose: () -> Unit,
) {
    val state = rememberWindowState(
        size = MainWin.dpSize,
        position = WindowPosition.Aligned(Alignment.Center),
    )
    var tick by remember { mutableIntStateOf(0) }
    tick.toString()
    val currentPage = koinInject<CurrentPage>()
    val page = getKoin().get<Page>(clazz = currentPage.pageClass)
    val textmap = koinInject<Textmap>()
    log.trace { "UI render tick #$tick" }
    val (toolbarWidth, toolbarOffset, toolbarAlpha, mainBoxModifier) = rememberMainWindowState(
        page = page,
        onTick = { tick++ },
    )
    MaterialTheme {
        RunOnceWithKoin(initState = initState)
        Window(
            title = title,
            onCloseRequest = onClose,
            resizable = false,
            state = state,
        ) {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) { focusRequester.requestFocus() }
            Box(
                modifier = mainBoxModifier
                    .focusRequester(focusRequester)
                    .focusable(),
            ) {
                page.invalidate()
                page.render(textmap)
                MainTextArea(textmap, tick)
                textmap.clear()
                Toolbar(
                    width = toolbarWidth,
                    xOffset = toolbarOffset,
                    bgAlpha = toolbarAlpha,
                )
            }
        }
    }
}

@Composable
@Suppress("FunctionName")
private fun RunOnceWithKoin(
    initState: (Koin) -> Unit,
) {
    var initialized by remember { mutableIntStateOf(0) }
    if (initialized == 0) {
        initialized = 1
        log.debug { "Compose is going to initialize state (initialized=$initialized)" }
        initState(getKoin())
    }
}

private const val TOOLBAR_ANIMATION_DURATION = 200

@Composable
@Suppress("CognitiveComplexMethod")
private fun rememberMainWindowState(
    page: Page,
    onTick: () -> Unit,
): MainWindowState {
    val density = LocalDensity.current
    val toolbarWidth = 40.dp
    val cursorLeftThreshold = with(density) { toolbarWidth.toPx() }
    var isCursorNearLeft by remember { mutableStateOf(false) }
    val toolbarOffset by animateDpAsState(
        targetValue = if (isCursorNearLeft) 0.dp else (-42).dp,
        animationSpec = tween(durationMillis = TOOLBAR_ANIMATION_DURATION),
    )
    val toolbarAlpha by animateFloatAsState(
        targetValue = if (isCursorNearLeft) 0.8f else 0.4f,
        animationSpec = tween(durationMillis = TOOLBAR_ANIMATION_DURATION),
    )
    val modifier = Modifier
        .fillMaxSize()
        .testTag(TestTags.mainBox)
        .border(MainWin.outerBorder, Color.fgColor)
        .padding(MainWin.outerBorder)
        .background(Color.bgColor)
        .padding(MainWin.innerMargin)
        .onPreviewKeyEvent { e ->
            val key = e.toKeyPressed() ?: return@onPreviewKeyEvent false
            page.onKeyPressed(key).also { isHandled ->
                if (isHandled) {
                    log.trace { "User entered: $key" }
                    onTick()
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
                        log.debug { "Mouse Move: Changing toolbar visibility to: $near" }
                        isCursorNearLeft = near
                    }
                }
            }
        }

    return MainWindowState(
        toolbarWidth = toolbarWidth,
        toolbarOffset = toolbarOffset,
        toolbarAlpha = toolbarAlpha,
        mainBoxModifier = modifier,
    )
}

private data class MainWindowState(
    val toolbarWidth: Dp,
    val toolbarOffset: Dp,
    val toolbarAlpha: Float,
    val mainBoxModifier: Modifier,
)
