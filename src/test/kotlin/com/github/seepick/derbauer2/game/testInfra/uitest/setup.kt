package com.github.seepick.derbauer2.game.testInfra.uitest

import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.initGame
import com.github.seepick.derbauer2.game.view.textengineModule
import com.github.seepick.derbauer2.textengine.compose.MainWindow
import org.koin.compose.KoinApplication
import org.koin.dsl.module

fun uitestModule() = module {
//    single { IgnoringBeeper } bind Beeper::class // TODO enable test beeper after confirmed it beeps without
}

fun ComposeTest.uiTest(testCode: () -> Unit) {
    ui.setContent {
        KoinApplication(application = {
            modules(textengineModule(), gameModule(this@uiTest::class), uitestModule())
        }) {
            MainWindow(title = "TestBauer2", { it.initGame() }, {})
        }
    }
    logGameText()
    testCode()
}
