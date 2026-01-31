package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.github.seepick.derbauer2.textengine.TestTags

@Suppress("FunctionName")
@Composable
fun MainTextArea(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxSize().testTag(TestTags.mainTextArea),
        style = MainWin.mainTextStyle,
    )
}
