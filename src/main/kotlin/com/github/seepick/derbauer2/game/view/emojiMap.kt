package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.game.core.Warning
import com.github.seepick.derbauer2.game.core.`warning ⚠️`
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.`citizen 🙎🏻‍♂️`
import com.github.seepick.derbauer2.game.resource.`food 🍖`
import com.github.seepick.derbauer2.game.resource.`gold 💰`
import com.github.seepick.derbauer2.game.resource.`land 🌍`
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.`happiness 🥳`
import com.github.seepick.derbauer2.game.tech.`knowledge 🧪`
import kotlin.reflect.KClass

private val emojiMap: Map<KClass<out Any>, Emoji?> = mapOf(
    Food::class to Emoji.`food 🍖`,
    Gold::class to Emoji.`gold 💰`,
    Citizen::class to Emoji.`citizen 🙎🏻‍♂️`,
    Land::class to Emoji.`land 🌍`,
    Knowledge::class to Emoji.`knowledge 🧪`,

    Happiness::class to Emoji.`happiness 🥳`,
    Warning::class to Emoji.Companion.`warning ⚠️`,
)

val KClass<*>.simpleNameEmojied: String
    get() = (simpleName ?: "!simpleName!") + (emojiMap[this]?.let { " $it" } ?: "")

val KClass<*>.emojiMappedOrSimpleName: String
    get() = emojiMappedOrNull ?: simpleName ?: "!simpleName!"

val KClass<*>.emojiMappedOrNull: String?
    get() = emojiMap[this]?.string
