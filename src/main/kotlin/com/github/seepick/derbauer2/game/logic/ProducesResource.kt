package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import kotlin.reflect.KClass

/** For now can only produce 1 type of resource. */
interface ProducesResource : EntityEffect {
    val producingResourceType: KClass<out Resource>
    val resourceProductionAmount: Units
}

/** For now can only store 1 type of resource. */
interface StoresResource : EntityEffect, Ownable {
    val storableResource: KClass<out StorableResource>
    val storageAmount: Units
    val totalStorageAmount: Units get() = owned * storageAmount
}
