package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.seepick.derbauer2.textengine.Derbauer2Theme

@Suppress("FunctionName")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tooltip(
    text: String?,
    offset: Boolean = true,
    content: @Composable () -> Unit,
) {
    if (text == null) {
        content()
    } else {
        TooltipArea(
            tooltip = {
                Surface(
                    modifier = Modifier
                        .shadow(4.dp)
                        .let { if (text.length > 60) it.width(400.dp) else it }
                        .alpha(0.8f),
                    color = Derbauer2Theme.fgColor,
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        text = text,
                        color = Derbauer2Theme.bgColor,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(10.dp, 0.dp),
                    )
                }
            },
            tooltipPlacement = TooltipPlacement.CursorPoint(
                alignment = Alignment.BottomEnd,
                offset = if (offset) DpOffset(3.dp, 3.dp) else DpOffset.Zero,
            ),
        ) {
            content()
        }
    }
}
