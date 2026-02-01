package com.github.seepick.derbauer2.textengine

import androidx.compose.ui.test.SemanticsMatcher

fun isToolbarVisible(expectVisible: Boolean): SemanticsMatcher =
    SemanticsMatcher("Expected toolbar to be ${if (expectVisible) "" else "in"}visible") { node ->
//            node.layoutInfo.getModifierInfo().single { it.modifier ... check node.background.color.alpha }
        val xPos = node.positionInWindow.x
        if (expectVisible) {
            xPos > 0f
        } else {
            xPos < 0f
        }
    }

