package com.github.seepick.derbauer2.engine

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface Page : Renderer, KeyListener {
}

interface KeyListener {
    fun onKeyPressed(key: KeyPressed): Boolean
}

interface Renderer {
    fun renderText(): String
}

class CurrentPage(
    page: KClass<out Page>
) {
    private val log = logger {}
    var page: KClass<out Page> = page
        set(value) {
            log.info { "Switching to controller: ${value.simpleName}" }
            field = value
        }
}
