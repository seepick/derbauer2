package com.github.seepick.derbauer2.game

import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class GameUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsWelcomeText() {
        composeTestRule.setContent {
            Text("DerBauer2")
        }

        composeTestRule.onNodeWithText("DerBauer2x").assertIsDisplayed()
    }
}
