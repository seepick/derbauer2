package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.stat.Happiness
import kotlin.reflect.KClass

val emojiMap: Map<KClass<out Any>, Emoji?> = mapOf(
    Food::class to Food.Data.emojiOrNull,
    Gold::class to Gold.Data.emojiOrNull,
    Citizen::class to Citizen.Data.emojiOrNull,
    Land::class to Land.Data.emojiOrNull,

    House::class to House.Data.emojiOrNull,
    Farm::class to Farm.Data.emojiOrNull,

    Happiness::class to Happiness.emoji,
)

val KClass<*>.simpleNameEmojied: String
    get() = (simpleName ?: "!simpleName!") + (emojiMap[this]?.let { " $it" } ?: "")

val KClass<*>.emojiOrSimpleName: String
    get() = emojiMap[this]?.value ?: simpleName ?: "!simpleName!"
