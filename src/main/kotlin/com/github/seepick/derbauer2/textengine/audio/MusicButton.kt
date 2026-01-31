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
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import org.koin.compose.koinInject

@Suppress("FunctionName")
@Composable
fun MusicButton(autoPlayMusic: Boolean, stateRepo: TextengineStateRepository) {
    val shuffler = koinInject<ListShuffler>()
    val player = remember {
        MusicPlayer(shuffler = shuffler)
    }
    var isPlaying by remember { mutableStateOf(false) }

    if (autoPlayMusic) {
        LaunchedEffect(Unit) {
            player.play()
            isPlaying = true
            stateRepo.setMusicPlaying(true)
        }
    }
    IconButton(onClick = {
        if (isPlaying) {
            player.stop()
            isPlaying = false
            stateRepo.setMusicPlaying(false)
        } else {
            player.play()
            isPlaying = true
            stateRepo.setMusicPlaying(true)
        }
    }) {
        Text(if (isPlaying) "⏸️" else "▶️", color = Color.White)
    }
    DisposableEffect(Unit) {
        onDispose {
            player.stop()
        }
    }
}
