package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

fun User.storageFor(resource: StorableResource) =
    storageFor(resource::class)

fun User.availableOf(resource: StorableResource) =
    storageFor(resource::class) - resource.owned

fun User.storageFor(resourceClass: KClass<out StorableResource>) =
    all
        .filterIsInstance<StoresResource>()
        .filter { it.storableResourceClass == resourceClass }
        .sumOf { it.totalStorageAmount.value }.z
