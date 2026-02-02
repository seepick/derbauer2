package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import kotlin.reflect.KClass

fun <R : Resource> User.enableAndSet(resource: R, amount: Z): R {
    enable(resource)
    // by-pass validation via transaction
    resource._setOwnedInternal = amount
    return resource
}

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Zz) {
    changes.shouldContainChange(resource, amount)
}

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Z) {
    shouldContainChange(resource, amount.zz)
}

fun List<ResourceChange>.shouldContainChange(resource: Resource, amount: Zz) {
    shouldContain(ResourceChange(resource, amount))
}

fun List<ResourceChange>.shouldContainChange(resource: Resource, amount: Z) {
    shouldContainChange(resource, amount.zz)
}

fun ResourceChanges.shouldBeEmpty() {
    changes.shouldBeEmpty()
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

fun foodProductionModifier(multiplier: Double) = ResourceProductionModifierStub(
    handlingResource = Food::class,
    modifier = { source -> (source.value.toDouble() * multiplier).z }
)

class ResourceProductionModifierStub(
    private val handlingResource: KClass<out Resource>,
    private val modifier: User.(Z) -> Z,
    override val labelSingular: String = "Foo",
) : Entity, ResourceProductionModifier {

    override fun handlesResource(resource: Resource) = resource::class == handlingResource
    override fun modifyAmount(user: User, source: Z) = user.modifier(source)
    override fun deepCopy() = this
}
