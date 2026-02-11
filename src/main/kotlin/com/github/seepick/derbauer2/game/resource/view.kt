@file:Suppress("ObjectPropertyName", "NonAsciiCharacters")

package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.common.emoji.emoji
import com.github.seepick.derbauer2.game.view.ViewOrder
import java.util.concurrent.atomic.AtomicInteger

private val goldEmoji = "💰️".emoji
val Emoji.Companion.`gold 💰` get() = goldEmoji

private val foodEmoji = "🍖".emoji
val Emoji.Companion.`food 🍖` get() = foodEmoji

private val landEmoji = "🌍".emoji
val Emoji.Companion.`land 🌍` get() = landEmoji

private val citizenEmoji = "🙎🏻‍♂️".emoji
val Emoji.Companion.`citizen 🙎🏻‍♂️` get() = citizenEmoji

val ViewOrder.Companion.Resource get() = ResourceOrder

object ResourceOrder {
    private val counter = AtomicInteger()

    val Gold = counter.incrementAndGet()
    val Food = counter.incrementAndGet()
    val Citizen = counter.incrementAndGet()
    val Land = counter.incrementAndGet()
    val Knowledge = counter.incrementAndGet()
}
