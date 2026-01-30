package com.github.seepick.derbauer2.textengine

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.oshai.kotlinlogging.KotlinLogging
import javazoom.jl.player.Player
import java.awt.Toolkit
import java.util.concurrent.atomic.AtomicReference

fun interface Beeper {
    fun beep(reason: String)
}

object RealBeeper : Beeper {
    private val log = KotlinLogging.logger {}

    override fun beep(reason: String) {
        Toolkit.getDefaultToolkit().beep()
        println("\uD83D\uDD14\uD83D\uDD14\uD83D\uDD14")
        log.debug { "Beep reason: $reason" }
    }
}

class MusicPlayer {
    private val playerRef = AtomicReference<Player?>()
    private val threadRef = AtomicReference<Thread?>()

    fun play() {
        val foo = this::class.java.getResourceAsStream("/audio/medieval_village.mp3")
        val thread = Thread {
            try {
                foo.use { fis ->
                    val player = Player(fis)
                    playerRef.set(player)
                    player.play() // blocking
                }
            } finally {
                playerRef.set(null)
                threadRef.set(null)
            }
        }.apply {
            isDaemon = true
            start()
        }
        threadRef.set(thread)
    }

    fun stop() {
        playerRef.getAndSet(null)?.close() // attempts to stop playback
        threadRef.getAndSet(null)?.interrupt()
    }
}

@Composable
@Suppress("FunctionNaming")
fun MusicPlayerView() {
    val player = remember { MusicPlayer() }
    var playing by remember { mutableStateOf(false) }

    Column {
        Button(onClick = {
            if (playing) {
                player.stop()
                playing = false
            } else {
                player.play()
                playing = true
            }
        }) {
            Text(if (playing) "Stop MP3" else "Play MP3")
        }
    }
    DisposableEffect(Unit) {
        onDispose { player.stop() }
    }
}
