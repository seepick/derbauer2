package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.game.core.Warning
import com.github.seepick.derbauer2.game.core.WarningListener
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.awt.Toolkit

class BeepingWarningListener(private val beeper: Beeper) : WarningListener {
    override fun onWarning(warning: Warning) {
        beeper.beep(reason = warning.message)
    }
}

fun interface Beeper {
    fun beep(reason: String)
}

object RealBeeper : Beeper {
    private val log = logger {}

    override fun beep(reason: String) {
        Toolkit.getDefaultToolkit().beep()
        println("\uD83D\uDD14\uD83D\uDD14\uD83D\uDD14")
        log.debug { "Beep reason: $reason" }
    }
}
