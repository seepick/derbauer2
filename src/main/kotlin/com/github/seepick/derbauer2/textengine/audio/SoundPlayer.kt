package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.common.ClasspathFile
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import javazoom.jl.player.Player
import java.util.concurrent.atomic.AtomicInteger

class SoundFile(path: String) : ClasspathFile(path) {
    companion object // for extensions
}

interface SoundPlayer {
    fun play(sound: SoundFile)
}

class RealSoundPlayer : SoundPlayer {
    private val log = logger {}
    private val threadId = AtomicInteger(0)

    private val soundBytesBuffer = mutableMapOf<String, ByteArray>()

    override fun play(sound: SoundFile) {
        Thread {
            log.trace { "playing $sound" }
            val soundBytes = soundBytesBuffer.getOrPut(sound.path) { sound.readBytes() }
            val player = Player(soundBytes.inputStream())
            player.play() // blocking call
        }.apply {
            name = "RealSoundPlayerThread#${threadId.getAndIncrement()}"
            isDaemon = true
            start()
        }
    }
}

object NoopSoundPlayer : SoundPlayer {
    private val log = logger {}
    override fun play(sound: SoundFile) {
        log.trace { "Ignore playing $sound" }
    }
}
