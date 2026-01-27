package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Z

interface Entity : DeepCopyable<Entity>, HasLabel, HasEmoji

/** A physical object (house, resource, people); not an abstract concept (tech) */
interface Asset : Entity, Ownable

interface EntityEffect


interface Ownable {
    @Suppress("DEPRECATION")
    val owned: Z get() = _setOwnedInternal

    @Suppress("PropertyName")
    @Deprecated("just don't use it unless you are within transaction application code")
    var _setOwnedInternal: Z

    // funny things possible ;) operator fun unaryMinus(): Zz = -owned
}