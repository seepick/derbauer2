package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.game.core.Warning
import com.github.seepick.derbauer2.game.core.WarningListener
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val warningSound = SoundFile("/sounds/warning.mp3")

class SoundWarningListener(private val soundPlayer: SoundPlayService) : WarningListener {
    private val log = logger {}

    override fun onWarning(warning: Warning) {
        log.debug { "🔔🔔🔔 $warning" }
        soundPlayer.play(warningSound)
    }
}

