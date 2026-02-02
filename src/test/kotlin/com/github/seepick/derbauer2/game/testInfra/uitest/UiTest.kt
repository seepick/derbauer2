package com.github.seepick.derbauer2.game.testInfra.uitest

import com.github.seepick.derbauer2.game.core.CollectingWarningListener
import com.github.seepick.derbauer2.game.core.WarningListener
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.initGame
import com.github.seepick.derbauer2.game.view.textengineModule
import com.github.seepick.derbauer2.textengine.audio.Beeper
import com.github.seepick.derbauer2.textengine.audio.IgnoringBeeper
import com.github.seepick.derbauer2.textengine.compose.MainWindow
import org.junit.experimental.categories.Category
import org.koin.compose.KoinApplication
import org.koin.dsl.bind
import org.koin.dsl.module

interface UiTestCategory

@Category(UiTestCategory::class)
interface UiTest

fun uiTestModule(listener: WarningListener) = module {
    single { IgnoringBeeper } bind Beeper::class
    single { listener }
}

class UiTestContext {
    val warningsCollector = CollectingWarningListener()
}

fun ComposeTest.uiTest(testCode: (UiTestContext) -> Unit) {
    val ctx = UiTestContext()
    ui.setContent {
        KoinApplication(application = {
            modules(textengineModule(), gameModule(this@uiTest::class), uiTestModule(ctx.warningsCollector))
        }) {
            MainWindow(title = "TestBauer2", { it.initGame() }, {})
        }
    }

    moveRealMouseOverWindow()
    logGameText()
    testCode(ctx)
}
