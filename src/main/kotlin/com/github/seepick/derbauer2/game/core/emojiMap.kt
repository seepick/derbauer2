package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.`citizen 🙎🏻‍♂️`
import com.github.seepick.derbauer2.game.resource.`food 🍖`
import com.github.seepick.derbauer2.game.resource.`gold 💰`
import com.github.seepick.derbauer2.game.resource.`land 🌍`
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.`happiness 🥳`
import kotlin.reflect.KClass

val emojiMap: Map<KClass<out Any>, Emoji?> = mapOf(
    Food::class to Emoji.`food 🍖`,
    Gold::class to Emoji.`gold 💰`,
    Citizen::class to Emoji.`citizen 🙎🏻‍♂️`,
    Land::class to Emoji.`land 🌍`,

    Happiness::class to Emoji.`happiness 🥳`,
    Warning::class to Emoji.`warning ⚠️`,
)

val KClass<*>.simpleNameEmojied: String
    get() = (simpleName ?: "!simpleName!") + (emojiMap[this]?.let { " $it" } ?: "")

val KClass<*>.emojiOrSimpleName: String
    get() = emojiMap[this]?.value ?: simpleName ?: "!simpleName!"
