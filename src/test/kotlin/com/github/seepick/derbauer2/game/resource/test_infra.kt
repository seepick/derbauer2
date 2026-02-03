package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import kotlin.reflect.KClass

fun <R : Resource> User.addResource(resource: R, amount: Z): R {
    add(resource)
    // by-pass validation via transaction
    resource._setOwnedInternal = amount
    return resource
}

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Zz) {
    changes.shouldContainChange(resource, amount)
}

fun ResourceChanges.shouldContainChange(resourceClass: KClass<out Resource>, amount: Zz) {
    changes.shouldContainChange(resourceClass, amount)
}

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Z) {
    shouldContainChange(resource, amount.zz)
}

fun List<ResourceChange>.shouldContainChange(resource: Resource, amount: Zz) {
    shouldContain(ResourceChange(resource, amount))
}

fun List<ResourceChange>.shouldContainChange(resourceClass: KClass<out Resource>, amount: Zz) {
    shouldContain(ResourceChange(resourceClass, amount))
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

inline fun <reified SR : StorableResource> User.givenFakeStorage(amount: Z) =
    FakeStorage(
        storableResourceClass = SR::class,
        storageAmount = amount
    ).also { add(it, disableCheck = true) }


interface ResourceProductionModifierEntity : Entity, ResourceProductionModifier

// need concrete classes (no abstract/lambda) to identify in User.enable/find

class ResourceProductionMultiplierStub(handlingResource: KClass<out Resource>, multiplier: Double) :
    AbstractResourceProductionMultiplierStub(handlingResource, multiplier)

class ResourceProductionMultiplierStub1(handlingResource: KClass<out Resource>, multiplier: Double) :
    AbstractResourceProductionMultiplierStub(handlingResource, multiplier)

class ResourceProductionMultiplierStub2(handlingResource: KClass<out Resource>, multiplier: Double) :
    AbstractResourceProductionMultiplierStub(handlingResource, multiplier)

abstract class AbstractResourceProductionMultiplierStub(
    handlingResource: KClass<out Resource>,
    multiplier: Double
) : AbstractResourceProductionModifierStub(
    handlingResource = handlingResource,
    modifier = { source -> (source.value.toDouble() * multiplier).toLong().zz },
)

abstract class AbstractResourceProductionModifierStub(
    override val handlingResource: KClass<out Resource>,
    private val modifier: User.(Zz) -> Zz,
) : ResourceProductionModifierEntity {
    override val labelSingular: String get() = this::class.simpleName!!
    override fun modifyAmount(user: User, source: Zz) = user.modifier(source)
    override fun deepCopy() = this
}
