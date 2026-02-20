package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.resource.ProducesResource
import com.github.seepick.derbauer2.game.resource.StoresResource
import com.github.seepick.derbauer2.game.view.HasEmoji
import com.github.seepick.derbauer2.game.view.RESOURCE_INFO_OWNED_SEPARATOR
import com.github.seepick.derbauer2.game.view.emojiHavingOrNull
import com.github.seepick.derbauer2.game.view.emojiMappedOrNull
import kotlin.reflect.KClass

interface Effect : Entity {
    fun toFormattedString(): String
}

fun Entity.toEffects(): List<Effect> = buildList {
    val entity = this@toEffects
    if (entity is ProducesResource) {
        add(ProducesResourceEffect(entity))
    }
    if (entity is StoresResource) {
        add(StoresResourceEffect(entity))
    }
    // TODO stat modifiers (theater)
}

fun List<Effect>.toFormattedString(): String = joinToString(", ") { effect ->
    effect.toFormattedString()
}

class ProducesResourceEffect<X>(val delegate: X) :
    ProducesResource by delegate, Effect, Entity by delegate
        where X : ProducesResource, X : Entity {
    override fun toFormattedString() =
        "${delegate.produceResourceAmount.toPrefixedString()}${RESOURCE_INFO_OWNED_SEPARATOR} ${producingResourceClass.emojiHack}"
}

class StoresResourceEffect<X>(val delegate: X) :
    StoresResource by delegate, Effect, Entity by delegate
        where X : StoresResource, X : Entity {
    override fun toFormattedString() =
        "${RESOURCE_INFO_OWNED_SEPARATOR}${storageAmount.toPrefixedString()} ${storableResourceClass.emojiHack}"

    override fun deepCopy() = this
}

// TODO nope... it's a design mistake, that: to get the emoji of a given resource, need `user` reference
// to get the real instance, instead of fiddling around with kotlin KClass shizzle...
val KClass<*>.emojiHack
    get() =
        emojiHavingOrNull ?: emojiMappedOrNull ?: error(
            "Emoji lookup hack failed for $qualifiedName " +
                    "(implement ${HasEmoji::class.qualifiedName} or register it in the emojiMap)"
        )
