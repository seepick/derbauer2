package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.EntityEffect
import com.github.seepick.derbauer2.game.logic.Ownable
import com.github.seepick.derbauer2.game.logic.Units
import kotlin.reflect.KClass

/** For now can only produce 1 type of resource. */
interface ProducesResource : EntityEffect {
    val producingResourceClass: KClass<out Resource>
    val producingResourceAmount: Units
}

interface ProducesResourceOwnable: ProducesResource, Ownable {
    val totalProducingResourceAmount get() = owned * producingResourceAmount
}

/** For now can only store 1 type of resource. */
interface StoresResource : EntityEffect, Ownable {
    val storableResourceClass: KClass<out StorableResource>
    val storageAmount: Units
    val totalStorageAmount: Units get() = owned * storageAmount
}
