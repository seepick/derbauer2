package com.github.seepick.derbauer2.textengine.mainWindow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.seepick.derbauer2.textengine.Derbauer2Theme
import com.github.seepick.derbauer2.textengine.TestTags
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import com.github.seepick.derbauer2.textengine.audio.MusicButton
import com.github.seepick.derbauer2.textengine.audio.SoundToggleButton
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.koinInject

private val log = logger {}

@Suppress("FunctionName")
@Composable
fun Toolbar(
    width: Dp,
    xOffset: Dp,
    bgAlpha: Float,
) {
    val stateRepo = koinInject<TextengineStateRepository>()
    val storedMusicPlayState = remember { stateRepo.getMusicPlaying() }
    log.trace { "Receiveing toolbar.xOffset: $xOffset" }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = xOffset)
                .width(width)
                .testTag(TestTags.toolbar)
                .clip(RoundedCornerShape(4.dp))
                .background(Derbauer2Theme.fgColor.copy(alpha = bgAlpha))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .padding(0.dp, 8.dp),
            ) {
                MusicButton(
                    autoPlayMusic = storedMusicPlayState ?: false,
                    stateRepo = stateRepo,
                )
                SoundToggleButton()
            }
        }
    }
}
