package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.resource.StorableResource
import kotlin.reflect.KClass

fun User.storageFor(resource: StorableResource): Units =
    storageFor(resource::class)

fun User.storageFor(resource: KClass<out StorableResource>): Units =
    all
        .filterIsInstance<StoresResource>()
        .filter { it.storableResource == resource }
        .sumOf { it.totalCapacity.single }.units
