package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.view.simpleNameEmojied
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class WarningBus(
    private val listeners: List<WarningListener>,
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
    TRADING_ZERO_BLOCKED,
}

data class Warning(
    val type: WarningType,
    val message: String,
) {
    override fun toString(): String = "${this::class.simpleNameEmojied}(type=$type, message='$message')"

    companion object {
        val emoji = Emoji.`warning ⚠️`
    }
}

fun interface WarningListener {
    fun onWarning(warning: Warning)
}

private val warningEmoji = "⚠️".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`warning ⚠️` get() = warningEmoji
