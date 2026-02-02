package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Ownable
import kotlin.reflect.KClass

/** For now can only produce 1 type of resource. */
interface ProducesResource {
    val producingResourceClass: KClass<out Resource>
    val producingResourceAmount: Z
}

interface ProducesResourceOwnable : ProducesResource, Ownable {
    val totalProducingResourceAmount get() = owned * producingResourceAmount
}

/** For now can only store 1 type of resource. */
interface StoresResource : Ownable {
    val storableResourceClass: KClass<out StorableResource>
    val storageAmount: Z
    val totalStorageAmount: Z get() = owned * storageAmount
}
