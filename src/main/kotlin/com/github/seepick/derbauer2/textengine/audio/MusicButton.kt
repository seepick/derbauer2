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
import com.github.seepick.derbauer2.game.prob.ListShuffler
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import com.github.seepick.derbauer2.textengine.compose.Tooltip
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.compose.koinInject

private val log = logger {}

@Suppress("FunctionName", "LongMethod")
@Composable
fun MusicButton(
    autoPlayMusic: Boolean,
    stateRepo: TextengineStateRepository,
//    viewModel: AudioViewModel = koinViewModel(),
) {
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
    Tooltip(if (isPlaying) "Stop Music" else "Play Music") {
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
            Text(if (isPlaying) "🔊" else "🔇")
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            log.debug { "disposing MusicButton" }
            player.stop()
        }
    }
}
