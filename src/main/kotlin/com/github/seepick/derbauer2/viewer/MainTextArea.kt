package com.github.seepick.derbauer2.viewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MainTextArea(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxSize(),
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            color = Color.fgColor,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
//            lineHeight = 15.sp,
        ),
    )
}
