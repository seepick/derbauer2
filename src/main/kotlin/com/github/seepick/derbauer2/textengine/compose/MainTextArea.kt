package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.Textmap

@Suppress("FunctionName")
@Composable
fun MainTextArea(textmap: Textmap) {
    Box(
        modifier = Modifier.fillMaxSize().testTag(TestTags.mainTextArea)
    ) {
        val grid = textmap.getGrid()
        val cellWidth = 10.85.dp
        val cellHeight = 22.2.dp

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell != " ") {
                    Text(
                        text = cell,
                        modifier = Modifier.offset(
                            x = cellWidth * colIndex,
                            y = cellHeight * rowIndex
                        ),
                        style = MainWin.mainTextStyle,
                    )
                }
            }
        }
    }
}
