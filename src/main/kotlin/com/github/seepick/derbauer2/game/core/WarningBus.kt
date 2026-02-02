package com.github.seepick.derbauer2.game.core

import io.github.oshai.kotlinlogging.KotlinLogging.logger

class WarningBus(
    private val listeners: List<WarningListener>
) {
    private val log = logger {}

    init {
        log.debug { "WarningBus initialized with ${listeners.size} listeners: $listeners" }
    }

    fun dispatch(warning: Warning) {
        log.debug { "Dispatching $warning" }
        listeners.forEach { it.onWarning(warning) }
    }
}

data class Warning(val message: String) // TODO introduce exhaustive warning types for testing purposes

fun interface WarningListener {
    fun onWarning(warning: Warning)
}
