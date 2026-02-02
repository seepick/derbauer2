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

enum class WarningType {
    OTHER,
    LAND_OVERUSE,
    INSUFFICIENT_RESOURCES,
    COMPOUND,
}

data class Warning(
    val type: WarningType,
    val message: String,
)

fun interface WarningListener {
    fun onWarning(warning: Warning)
}
