package com.github.seepick.derbauer2.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performMouseInput
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.pageParser.keyForSelectOption
import com.github.seepick.derbauer2.game.testInfra.pageParser.readSingleResource
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeTest
import com.github.seepick.derbauer2.game.testInfra.uitest.UiTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import com.github.seepick.derbauer2.textengine.TestTags
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.junit.Rule
import org.junit.Test

class BuildingUiTest : UiTest, ComposeTest {

    override val log = logger {}
    @get:Rule
    override val ui = createComposeRule()

    @Test
    fun `When build house Then change assets`() = uiTest {
        gameText.contentLinesString shouldContainIgnoringCase "you are home"
        val initialGold = gameText.readSingleResource(Gold.Data.emojiOrNull).owned

        pressKey(gameText.keyForSelectOption("build"))
        pressKey(gameText.keyForSelectOption("house"))

        ui.waitForIdle() // there was once a flaky test on build server (i guess too quick assert execution?!)
        gameText.readSingleResource(Gold.Data.emojiOrNull).owned shouldBeLessThan initialGold
    }
    // TEST: invalid build
}

// TEST: class NextTurnReportUiTest : UiTest, ComposeTest {

// TEST: class TradeUiTest : UiTest, ComposeTest {
//    buy/sell/fail

class ToolbarUiTest : UiTest, ComposeTest {

    override val log = logger {}
    @get:Rule
    override val ui = createComposeRule()
    private val anyYPos = 0.0f
    private val xPosWithinThreshold = 10.0f

    @Test
    fun `When move cursor to the left edge Then toolbar moves right`() = uiTest {
        val toolbar = ui.onNodeWithTag(TestTags.toolbar)
        toolbar.assert(isToolbarVisible(false))
        ui.onNodeWithTag(TestTags.mainBox).performMouseInput {
            moveTo(Offset(xPosWithinThreshold, anyYPos))
        }
        toolbar.assert(isToolbarVisible(true))
    }
    // TEST: music player button works
}

fun isToolbarVisible(expectVisible: Boolean): SemanticsMatcher =
    SemanticsMatcher("toolbar in") { node ->
//            node.layoutInfo.getModifierInfo().single { it.modifier ... check node.background.color.alpha }
        val xPos = node.positionInWindow.x
        if (expectVisible) {
            xPos > 0f
        } else {
            xPos < 0f
        }
    }

