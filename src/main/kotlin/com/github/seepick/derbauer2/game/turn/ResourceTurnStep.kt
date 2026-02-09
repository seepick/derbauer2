package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

interface ResourceTurnStep {
    val order: Int
    fun calcChanges(): ResourceChanges

    companion object // for extension functions

    object Order {
        private val incrementor = AtomicInteger(0)
        // declared order is of utmost significance!
        val stat = incrementor.getAndIncrement()
        val producesResourcesAndCitizen = incrementor.getAndIncrement()
        val tax = incrementor.getAndIncrement()
    }
}

abstract class DefaultResourceTurnStep(
    val user: User,
    override val order: Int,
    private val requiresEntities: List<KClass<out Entity>>,
) : ResourceTurnStep {

    protected abstract fun calcChangesChecked(): ResourceChanges

    final override fun calcChanges(): ResourceChanges =
        if (requiresEntities.all { user.hasEntity(it) }) {
            calcChangesChecked()
        } else {
            ResourceChanges.empty
        }
}
