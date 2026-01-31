package com.github.seepick.derbauer2.textengine.audio

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.github.seepick.derbauer2.game.probability.ListShuffler
import org.koin.compose.koinInject

@Suppress("FunctionName")
@Composable
fun MusicButton(autoPlayMusic: Boolean, stateManager: MusicStateManager) {
    val shuffler = koinInject<ListShuffler>()
    val player = remember {
        MusicPlayer(shuffler = shuffler)
    }
    var playing by remember { mutableStateOf(false) }

    if (autoPlayMusic) {
        LaunchedEffect(Unit) {
            player.play()
            playing = true
            stateManager.updatePlayingState(true)
        }
    }
    IconButton(onClick = {
        if (playing) {
            player.stop()
            playing = false
            stateManager.updatePlayingState(false)
        } else {
            player.play()
            playing = true
            stateManager.updatePlayingState(true)
        }
    }) {
        Text(if (playing) "⏸️" else "▶️", color = Color.Companion.White)
    }
    DisposableEffect(Unit) {
        onDispose {
            player.stop()
            stateManager.updatePlayingState(false)
        }
    }
}
