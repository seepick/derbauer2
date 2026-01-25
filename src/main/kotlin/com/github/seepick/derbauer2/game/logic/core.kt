package com.github.seepick.derbauer2.game.logic

import kotlin.reflect.KClass

interface Entity {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    val emoji: String? get() = null
}

interface Technology : Entity {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
}

interface ProducesResource : Entity {
    val resourceType: KClass<out Resource>
    fun produce(): Int
}

sealed interface BuyResult {
    object Success : BuyResult
    object NotEnoughGold : BuyResult
}
