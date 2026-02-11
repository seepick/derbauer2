package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.common.emoji.Emoji
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class EmojiTest : StringSpec({
    listOf("😀", "🚙", "✅", "🤝", "🏴‍☠️").forEach { valid ->
        "success for [$valid]" {
            Emoji(valid).string shouldBeEqual valid
        }
    }
    "toString" {
        Emoji("✅").toString() shouldBeEqual "✅"
    }
    listOf("", " ", ".", "x", "❌❌").forEach { invalidEmojiValue ->
        "fail for [$invalidEmojiValue]" {
            shouldThrow<IllegalArgumentException> {
                Emoji(invalidEmojiValue)
            }
        }
    }
})
