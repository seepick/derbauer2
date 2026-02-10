@file:Suppress("MagicNumber")

package com.github.seepick.derbauer2.game.common

@JvmInline
value class Emoji(val value: String) {
    init {
        value.requireIsSingleEmoji()
    }

    override fun toString() = value

    companion object
}

val String.emoji get() = Emoji(this)
