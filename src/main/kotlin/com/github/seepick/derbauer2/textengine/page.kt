package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface Page : Renderer, KeyListener {
    /** reset prompts */
    fun invalidate() {
        // default no-op
    }
}

fun interface Renderer {
    fun render(textmap: Textmap)
}

private val pageEmoji = "📄".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`page 📄` get() = pageEmoji

class CurrentPage(
    pageClass: KClass<out Page>,
) {
    private val log = logger {}

    var current: KClass<out Page> = pageClass
        set(value) {
            log.info { "${Emoji.`page 📄`} ======> setting PAGE to: ${value.simpleName}" }
            field = value
        }
}
