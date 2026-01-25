package com.github.seepick.derbauer2.textengine

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface Page : Renderer, KeyListener

interface Renderer {
    fun renderText(textmap: Textmap)
}

class CurrentPage(
    page: KClass<out Page>
) {
    private val log = logger {}
    var page: KClass<out Page> = page
        set(value) {
            log.info { "Set: ${value.simpleName}" }
            field = value
        }
}
