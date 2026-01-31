package com.github.seepick.derbauer2.textengine.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.seepick.derbauer2.textengine.audio.MusicButton
import com.github.seepick.derbauer2.textengine.audio.MusicStateManager
import com.github.seepick.derbauer2.textengine.fgColor
import org.koin.compose.koinInject

@Suppress("FunctionName")
@Composable
fun Toolbar(width: Dp, xOffset: Dp, bgAlpha: Float) {
    val stateManager = koinInject<MusicStateManager>()
    val autoPlayMusic = stateManager.shouldAutoPlay()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.Companion
                .align(Alignment.CenterStart)
                .offset(x = xOffset)
                .width(width)
                .fillMaxHeight()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.fgColor.copy(alpha = bgAlpha))
        ) {
            MusicButton(autoPlayMusic = autoPlayMusic, stateManager = stateManager)
        }
    }
}
