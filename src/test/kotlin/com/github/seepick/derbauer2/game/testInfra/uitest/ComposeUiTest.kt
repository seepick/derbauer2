package com.github.seepick.derbauer2.game.testInfra.uitest

import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyPress
import com.github.seepick.derbauer2.game.core.DerBauer2SysProp
import com.github.seepick.derbauer2.game.testInfra.pageParser.GamePageParser
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import com.github.seepick.derbauer2.textengine.keyboard.asComposeKey
import io.github.oshai.kotlinlogging.KLogger
import org.junit.Before
import org.junit.experimental.categories.Category

interface UiTestCategory

/** See: https://www.scribd.com/document/759389692/compose-testing-cheatsheet */
@Suppress("ComplexInterface")
@Category(UiTestCategory::class)
interface ComposeUiTest {

    val log: KLogger
    val ui: ComposeContentTestRule

    val gameTextNode get() = ui.onNodeWithTag(TestTags.mainTextArea).assertIsDisplayed()
    val gameText get() = GamePageParser(gameTextAsString)
    val gameTextAsString
        get(): String {
            val node = gameTextNode.fetchSemanticsNode()
            val textList = node.config.getOrNull(SemanticsProperties.Text)
            return textList?.joinToString(separator = "") { it.toString() } ?: error("No text found!")
        }

    @Before
    fun disalbeMacosQuitHandlerDuringTests() {
        // would otherwise require for each test exec to add JVM arg for AWT export...
        System.setProperty(DerBauer2SysProp.DISABLE_MACOS_QUIT_HANDLER.key, "true")
    }

    @OptIn(InternalComposeUiApi::class)
    fun pressKey(key: Key) {
        log.trace { "pressKey($key)" }
        gameTextNode.performKeyPress(KeyEvent(key, KeyEventType.KeyDown))
        logGameText()
    }

    fun pressKey(printChar: PrintChar.Numeric) {
        pressKey(printChar.asComposeKey)
    }

    fun logGameText() {
        log.debug { "Game Text:\n$gameTextAsString" }
    }
}
