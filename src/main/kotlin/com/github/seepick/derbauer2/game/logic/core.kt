package com.github.seepick.derbauer2.game.logic

import kotlin.reflect.KClass

interface Entity {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    val emoji: String? get() = null
    val emojiWithSpaceSuffixOrEmpty: String get() = emoji?.let { "$it " } ?: ""
}

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity

interface Technology : Entity {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
}

interface EntityFeature

interface ProducesResource : EntityFeature{
    val resourceType: KClass<out Resource>
    fun produce(): Units
}

interface StoresResource : EntityFeature {
    val storableResource: KClass<out StorableResource>
    val capacity: Units
    val totalCapacity: Units
}

sealed interface BuildResult {
    object Success : BuildResult
    object NotEnoughGold : BuildResult
}
