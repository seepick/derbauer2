package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.StoresResource
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
