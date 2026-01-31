package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Suppress("FunctionName")
@Composable
fun MainTextArea(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxSize(),
        style = MainWin.mainTextStyle,
    )
}
