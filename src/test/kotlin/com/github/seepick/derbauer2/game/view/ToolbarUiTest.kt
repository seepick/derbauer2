package com.github.seepick.derbauer2.game.view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performMouseInput
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeUiTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.isToolbarVisible
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.junit.Rule
import org.junit.Test

// actually should be part of textengine, but leave it for now as it work require some infra-rework :)
class ToolbarUiTest : ComposeUiTest {

    override val log = logger {}
    @get:Rule
    override val ui = createComposeRule()
    private val anyYPos = 0.0f
    private val xPosWithinThreshold = 10.0f

    @Test
    fun `When move cursor to the left edge Then toolbar moves right`() = uiTest {
        ui.onNodeWithTag(TestTags.toolbar).assert(isToolbarVisible(false))
        ui.onNodeWithTag(TestTags.mainBox).performMouseInput {
            moveTo(Offset(xPosWithinThreshold, anyYPos), 50)
        }

        ui.mainClock.advanceTimeBy(500L)
        Thread.sleep(50)
        ui.onNodeWithTag(TestTags.toolbar).assert(isToolbarVisible(true))
    }
    // TEST: music player button works; get Text of Button
//    log.debug { ui.onNodeWithTag(TestTags.mainBox).printToString() }
}
