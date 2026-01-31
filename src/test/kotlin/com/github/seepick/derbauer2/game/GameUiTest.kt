package com.github.seepick.derbauer2.game

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.pageParser.keyForSelectOption
import com.github.seepick.derbauer2.game.testInfra.pageParser.readSingleResource
import com.github.seepick.derbauer2.game.testInfra.uitest.ComposeTest
import com.github.seepick.derbauer2.game.testInfra.uitest.uiTest
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.junit.Rule
import org.junit.Test

class GameUiTest : ComposeTest {

    override val log: KLogger = logger {}
    @get:Rule
    override val ui: ComposeContentTestRule = createComposeRule()

    @Test
    fun `When build house Then change assets`() = uiTest {
        gameText.contentLinesString shouldContainIgnoringCase "you are home"
        val initialGold = gameText.readSingleResource(Gold.Data.emojiOrNull).owned

        pressKey(gameText.keyForSelectOption("build"))
        pressKey(gameText.keyForSelectOption("house"))

        gameText.readSingleResource(Gold.Data.emojiOrNull).owned shouldBeLessThan initialGold
    }

    // TEST: invalid build
    // TEST: next turn report
    // TEST: trade, buy/sell/fail
    // TEST: move pointer to left, toolbar swipes open
    // TEST: music player button works
}
