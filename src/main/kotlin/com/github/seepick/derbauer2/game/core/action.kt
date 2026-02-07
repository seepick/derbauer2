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
interface Action

class ActionsCollector : ActionBusListener {

    private val log = logger {}
    private val actions = mutableListOf<Action>()
    override fun onAction(action: Action) {
        log.trace { "collecting: $action" }
        actions += action
    }

    fun getAll(): List<Action> = actions.toList()
    fun getAllAndClear(): List<Action> = getAll().also {
        log.debug { "getAllAndClear() ... actions.size=${actions.size}" }
        actions.clear()
    }
}
