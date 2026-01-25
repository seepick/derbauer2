package com.github.seepick.derbauer2.textengine

import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.Toolkit

interface Beeper {
    fun beep(reason: String)
}

 object RealBeeper : Beeper {
    private val log = KotlinLogging.logger {}

    override fun beep(reason: String) {
        log.debug { "Beep reason: $reason" }
        Toolkit.getDefaultToolkit().beep()
        println("\uD83D\uDD14\uD83D\uDD14\uD83D\uDD14")
    }
}
