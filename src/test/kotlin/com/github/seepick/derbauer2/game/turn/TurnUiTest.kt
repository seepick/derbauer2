package com.github.seepick.derbauer2.game.turn

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeUiTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.junit.Rule
import org.junit.Test

class TurnUiTest : ComposeUiTest {

    override val log = logger {}
    @get:Rule
    override val ui = createComposeRule()

    @Test
    fun `When press enter Then report rendered`() = uiTest {
        pressKey(Key.Enter)

        ui.waitForIdle()
        gameText.contentString shouldContainIgnoringCase "T U R N   R E P O R T"
    }
}
