package com.github.seepick.derbauer2.textengine.audio

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.awt.Toolkit

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
