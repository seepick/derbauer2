@file:Suppress("MagicNumber")

package com.github.seepick.derbauer2.game.common

@JvmInline
value class Emoji(val string: String) {
    init {
        string.requireIsSingleEmoji()
    }

    override fun toString() = string

    companion object
}

val String.emoji get() = Emoji(this)
