package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.seepick.derbauer2.textengine.MatrixSize
import com.github.seepick.derbauer2.textengine.fgColor

object MainWin {

    val matrixSize = MatrixSize(rows = 25, cols = 80)
    val dpSize: DpSize

    val outerBorder = 10.dp
    val innerMargin = 5.dp

    val cellWidth = 10.85.dp
    val cellHeight = 22.2.dp
    private val mainContentWidth = cellWidth * matrixSize.cols
    // Add extra vertical space to ensure last line is fully visible with absolute positioning
    private val mainContentHeight = cellHeight * matrixSize.rows + cellHeight * 1.3f

    // FontFamily.Monospace ... NO! bug fix emojis, line height changes, jumping UI!
    private val monoPlusEmoji = FontFamily(Font(resource = "JetBrainsMono-Regular.ttf"))
    private val mainFontSize = 18.sp
    val mainTextStyle = TextStyle(
        fontFamily = monoPlusEmoji,
        fontSize = mainFontSize,
        color = Color.fgColor,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        lineHeight = mainFontSize * 1.16,
    )

    init {
        val borderAndMarginGap = outerBorder.times(2) + innerMargin.times(2)
        dpSize = DpSize(
            width = mainContentWidth + borderAndMarginGap,
            height = mainContentHeight + borderAndMarginGap, // + 100.dp,
        )
    }
}
