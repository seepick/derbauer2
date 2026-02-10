package com.github.seepick.derbauer2.game.building

import androidx.compose.ui.test.junit4.createComposeRule
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.resource.`gold 💰`
import com.github.seepick.derbauer2.game.testInfra.pageParser.keyForSelectOption
import com.github.seepick.derbauer2.game.testInfra.pageParser.readSingleResource
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeUiTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.Rule
import org.junit.Test

class BuildingUiTest : ComposeUiTest {

    override val log = logger {}
    @get:Rule
    override val ui = createComposeRule()

    @Test
    fun `When build tent Then change assets`() = uiTest {
        val initialGold = gameText.readSingleResource(Emoji.`gold 💰`).owned

        pressKey(gameText.keyForSelectOption("build"))
        pressKey(gameText.keyForSelectOption("tent"))

        ui.mainClock.advanceTimeBy(500L)
        gameText.readSingleResource(Emoji.`gold 💰`).owned shouldBeLessThan initialGold
    }

    @Test
    fun `When build many farms Then fail`() = uiTest { ctx ->
        pressKey(gameText.keyForSelectOption("build"))
        repeat(3) {
            pressKey(gameText.keyForSelectOption("farm"))
        }

        ui.mainClock.advanceTimeBy(500L)
        ctx.warningsCollector.warnings.shouldBeSingleton().first().type shouldBeEqual WarningType.LAND_OVERUSE
    }
}

// TEST: class NextTurnReportUiTest : UiTest, ComposeTest {
// TEST: class TradeUiTest : UiTest, ComposeTest {
