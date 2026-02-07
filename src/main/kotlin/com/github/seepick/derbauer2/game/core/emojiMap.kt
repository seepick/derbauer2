package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.stat.Happiness
import kotlin.reflect.KClass

val emojiMap: Map<KClass<out Any>, String> = mapOf(
    Food::class to Food.Data.emojiOrNull.value,
    Gold::class to Gold.Data.emojiOrNull.value,
    Citizen::class to Citizen.Data.emojiOrNull.value,
    Land::class to Land.Data.emojiOrNull.value,

    House::class to (House.Data.emojiOrNull?.value ?: ""),
    Farm::class to (Farm.Data.emojiOrNull?.value ?: ""),

    Happiness::class to (Happiness.emoji.value),
)

val KClass<*>.simpleNameEmojied: String
    get() = (simpleName ?: "!simpleName!") + (emojiMap[this]?.let { " $it" } ?: "")

val KClass<*>.emojiOrSimpleName: String
    get() = emojiMap[this] ?: simpleName ?: "!simpleName!"
