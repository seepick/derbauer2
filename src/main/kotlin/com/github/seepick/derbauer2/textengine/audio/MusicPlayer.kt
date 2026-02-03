package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.game.prob.ListShuffler
import com.github.seepick.derbauer2.game.prob.randListOf
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import javazoom.jl.player.Player
import java.io.InputStream
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class MusicPlayer(
    private val shuffler: ListShuffler,
) {
    private val log = logger {}
    private val playerRef = AtomicReference<Player?>()
    private val threadRef = AtomicReference<Thread?>()
    private val threadId = AtomicInteger(0)

    // turned out tricky to get files in a folder by scanning it during runtime (packaged via compose in a fat jar)
    private val audioFilePaths = randListOf(
        "adventurers-quest.mp3",
        "another-medieval-village.mp3",
        "medieval-village.mp3",
        "rise-of-a-kingdom.mp3",
        "travelers-inn.mp3",
        "wild-boars-inn.mp3",
        shuffler = shuffler,
    )

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
        val path = "/bg_music/${audioFilePaths.next()}"
        log.debug { "auto-play next: $path" }
        val stream = MusicPlayer::class.java.getResourceAsStream(path) ?: error("Audio file not found at [$path]!")
        playStream(stream)
    }

    fun playStream(audioStream: InputStream) {
        stopped.set(false)
        val thread = Thread {
            try {
                audioStream.use { audioStream ->
                    val player = Player(audioStream)
                    playerRef.set(player)
                    player.play() // this is blocking
                    if (!stopped.get()) {
                        // notify only if playback finished naturally (not stopped)
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
