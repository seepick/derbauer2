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
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import javazoom.jl.player.Player
import java.io.InputStream
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class MusicPlayer {
    private val log = logger {}
    private val playerRef = AtomicReference<Player?>()
    private val threadRef = AtomicReference<Thread?>()
    private val threadId = AtomicInteger(0)

    // turned out tricky to get files in a folder by scanning it during runtime (packaged via compose in a fat jar)
    private val audioFilePaths = listOf(
        "adventurers-quest.mp3",
        "another-medieval-village.mp3",
        "medieval-village.mp3",
        "rise-of-a-kingdom.mp3",
        "travelers-inn.mp3",
        "wild-boars-inn.mp3",
    ).map { "/bg_music/$it" }

    private var currentAudioIdx = 0
    private val stopped = AtomicReference(false)

    fun play() {
        log.info { "play" }
        playNext()
    }

    fun stop() {
        log.info { "stop" }
        stopped.set(true)
        playerRef.getAndSet(null)?.close() // attempts to stop playback
        threadRef.getAndSet(null)?.interrupt()
    }

    private fun playNext() {
        val path = nextAudioFilePath()
        log.debug { "auto-play next: $path" }
        val stream = MusicPlayer::class.java.getResourceAsStream(path) ?: error("Audio file not found at [$path]!")
        playStream(stream)
    }

    private fun nextAudioFilePath(): String {
        if (currentAudioIdx == audioFilePaths.size) {
            currentAudioIdx = 0
        }
        return audioFilePaths[currentAudioIdx++]
    }

    fun playStream(audioStream: InputStream) {
        stopped.set(false)
        val thread = Thread {
            try {
                audioStream.use { audioStream ->
                    val player = Player(audioStream)
                    playerRef.set(player)
                    player.play() // this is blocking
                    // notify only if playback finished naturally (not stopped)
                    if (!stopped.get()) {
                        playNext()
                    }
                }
            } finally {
                playerRef.set(null)
                threadRef.set(null)
            }
        }.apply {
            name = "MusicPlayerThread#${threadId.getAndIncrement()}"
            isDaemon = true
            start()
        }
        threadRef.set(thread)
    }
}

@Composable
@Suppress("FunctionNaming")
fun MusicButton(autoPlayMusic: Boolean) {
    val player = remember { MusicPlayer() }
    var playing by remember { mutableStateOf(false) }

    if (autoPlayMusic) {
        LaunchedEffect(Unit) {
            player.play()
            playing = true
        }
    }
    IconButton(onClick = {
        if (playing) {
            player.stop()
            playing = false
        } else {
            player.play()
            playing = true
        }
    }) {
        Text(if (playing) "⏸️" else "▶️", color = Color.White)
    }
    DisposableEffect(Unit) {
        onDispose { player.stop() }
    }
}
