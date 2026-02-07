package com.github.seepick.derbauer2.game.core

import io.github.oshai.kotlinlogging.KotlinLogging.logger

interface ActionBus {
    fun dispatch(action: Action)
}

class ActionBusImpl(
    private val listeners: List<ActionBusListener>
) : ActionBus {

    private val log = logger {}

    override fun dispatch(action: Action) {
        log.trace { "dispatching action $action" }
        listeners.forEach { it.onAction(action) }
    }
}

fun interface ActionBusListener {
    fun onAction(action: Action)
}

/** Any user action. */
interface Action {
}

