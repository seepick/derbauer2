package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import io.kotest.matchers.collections.shouldContain
import kotlin.reflect.KClass

fun <R : Resource> User.enableAndSet(resource: R, amount: Z): R {
    enable(resource)
    // by-pass validation via transaction
    resource._setOwnedInternal = amount
    return resource
}

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Zz) {
    changes.shouldContainChange(resource to amount)
}

infix fun List<ResourceChange>.shouldContainChange(resourceAndAmount: Pair<Resource, Zz>) {
    shouldContain(ResourceChange(resourceAndAmount.first, resourceAndAmount.second))
}

data class FakeStorage<SR : StorableResource>(
    override val storableResourceClass: KClass<out SR>,
    override val storageAmount: Z,
    override var _setOwnedInternal: Z = 1.z
) : Entity, StoresResource {
    override val labelSingular = "test label"
    override fun deepCopy() = copy()
}

inline fun <reified SR : StorableResource> User.givenStorage(amount: Z) =
    FakeStorage(
        storableResourceClass = SR::class,
        storageAmount = amount
    ).also { enable(it, disableCheck = true) }
