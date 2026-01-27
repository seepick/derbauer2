package com.github.seepick.derbauer2.textengine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

// FontFamily.Monospace ... NO! bug fix emojis, line height changes, jumping UI!
private val monoPlusEmoji = FontFamily(Font(resource = "JetBrainsMono-Regular.ttf"))
private val mainFontSize = 18.sp
private val mainTextStyle = TextStyle(
    fontFamily = monoPlusEmoji,
    fontSize = mainFontSize,
    color = Color.fgColor,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    lineHeight = mainFontSize * 1.16,
)

@Suppress("FunctionName")
@Composable
fun MainTextArea(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxSize(),
        style = mainTextStyle,
    )
}
