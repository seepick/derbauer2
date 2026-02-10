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
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging

private val log = KotlinLogging.logger {}

@Suppress("FunctionName")
@Composable
fun MainTextArea(textmap: Textmap, tick: Long) {
    val fullText = textmap.toFullString()
    log.trace { "MainTextArea - UI render tick #$tick" }// have to access it, to trigger recomposition
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.mainTextArea)
            .semantics { text = AnnotatedString(fullText) }
    ) {
        renderGrid(textmap, tick)
    }
}

@Composable
private fun renderGrid(textmap: Textmap, tick: Long) {
    log.trace { "renderGrid - UI render tick #$tick" }// have to access it, to trigger recomposition
    textmap.toGrid().forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, cell ->
            if (cell != " ") {
                Text(
                    text = cell,
                    modifier = Modifier.offset(
                        x = MainWin.cellWidth * colIndex,
                        y = MainWin.cellHeight * rowIndex
                    ),
                    style = MainWin.mainTextStyle,
                )
            }
        }
    }
}
