package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.Textmap

@Suppress("FunctionName")
@Composable
fun MainTextArea(textmap: Textmap) {
    val fullText = textmap.toFullString()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.mainTextArea)
            .semantics { text = AnnotatedString(fullText) }
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
