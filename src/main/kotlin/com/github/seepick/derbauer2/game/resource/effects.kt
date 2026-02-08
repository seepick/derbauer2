package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Ownable
import kotlin.reflect.KClass

interface ProducesResource {
    val producingResourceClass: KClass<out Resource>
    val produceResourceAmount: Z
}

val <T> T.totalProduceResourceAmount where T : ProducesResource, T : Ownable
    get() =
        owned * produceResourceAmount

@Suppress("VariableMaxLength")
val ProducesResource.totalOrSimpleProduceResourceAmount
    get() = if (this is Ownable) {
        totalProduceResourceAmount
    } else {
        produceResourceAmount
    }

interface StoresResource {
    val storableResourceClass: KClass<out StorableResource>
    val storageAmount: Z
}

val <T> T.totalStorageAmount where T : StoresResource, T : Ownable
    get() =
        owned * storageAmount

val StoresResource.totalOrSimpleStorageAmount
    get() = if (this is Ownable) {
        totalStorageAmount
    } else {
        storageAmount
    }
