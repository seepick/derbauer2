package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import kotlin.reflect.KClass

interface Entity {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    fun labelFor(units: Units) = if (units == 1.units) labelSingular else labelPlural
    val emoji: String? get() = null
    val emojiWithSpaceSuffixOrEmpty: String get() = emoji?.let { "$it " } ?: ""
}

interface Ownable {
    var owned: Units
}

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity, Ownable {
}

interface Technology : Entity {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
}

interface EntityFeature

/** For now can only produce 1 type of resource. */
interface ProducesResource : EntityFeature {
    val producingResourceType: KClass<out Resource>
    val resourceProductionAmount: Units
}

/** For now can only store 1 type of resource. */
interface StoresResource : EntityFeature, Ownable {
    val storableResource: KClass<out StorableResource>
    val storageAmount: Units
    val totalStorageAmount: Units get() = owned * storageAmount
}
