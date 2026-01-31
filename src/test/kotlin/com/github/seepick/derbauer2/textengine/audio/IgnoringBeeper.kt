package com.github.seepick.derbauer2.textengine.audio

import io.github.oshai.kotlinlogging.KotlinLogging.logger

object IgnoringBeeper : Beeper {
    private val log = logger {}
    override fun beep(reason: String) {
        log.debug { "Ignoring beep: $reason" }
    }
}
