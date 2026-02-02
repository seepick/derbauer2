package com.github.seepick.derbauer2.game.building

import androidx.compose.ui.test.junit4.createComposeRule
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.pageParser.keyForSelectOption
import com.github.seepick.derbauer2.game.testInfra.pageParser.readSingleResource
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeTest
import com.github.seepick.derbauer2.game.testInfra.uitest.UiTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.equals.shouldBeEqual
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

        ui.mainClock.advanceTimeBy(500L)
        gameText.readSingleResource(Gold.Data.emojiOrNull).owned shouldBeLessThan initialGold
    }

    @Test
    fun `When build many farms Then fail`() = uiTest { ctx ->
        gameText.contentLinesString shouldContainIgnoringCase "you are home"

        pressKey(gameText.keyForSelectOption("build"))
        repeat(4) {
            pressKey(gameText.keyForSelectOption("farm"))
        }

        ui.mainClock.advanceTimeBy(500L)
        ctx.warningsCollector.warnings.shouldBeSingleton().first().type shouldBeEqual WarningType.LAND_OVERUSE
    }
}

// TEST: class NextTurnReportUiTest : UiTest, ComposeTest {

// TEST: class TradeUiTest : UiTest, ComposeTest {
//    buy/sell/fail
