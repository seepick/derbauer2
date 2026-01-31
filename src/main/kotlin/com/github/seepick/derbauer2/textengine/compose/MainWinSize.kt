package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.github.seepick.derbauer2.textengine.MatrixSize

object MainWinSize {

    val matrixSize = MatrixSize(rows = 25, cols = 80)
    val dpSize: DpSize

    val outerBorder = 10.dp
    val innerMargin = 5.dp

    private val mainContentWidth = 10.85.dp * matrixSize.cols
    private val mainContentHeight = 22.2.dp * matrixSize.rows

    init {
        val borderAndMarginGap = outerBorder.times(2) + innerMargin.times(2)
        dpSize = DpSize(
            width = mainContentWidth + borderAndMarginGap,
            height = mainContentHeight + borderAndMarginGap, // + 100.dp,
        )
    }
}
