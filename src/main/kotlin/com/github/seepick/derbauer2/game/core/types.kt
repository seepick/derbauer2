package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Z

interface Entity : DeepCopyable<Entity>, HasLabel, HasEmoji

fun interface DeepCopyable<T> {
    fun deepCopy(): T
}

/**
 * A physical object (house, resource, people); not an abstract concept (tech).
 * PS: itest assumes that all Assets have a zero-argument constructor; see GivenDsl#createAssetInstance
 */
interface Asset : Entity, Ownable

interface EntityEffect


interface Ownable {
    val owned: Z get() = _setOwnedInternal

    @Suppress("PropertyName", "VariableNaming")
    var _setOwnedInternal: Z

    // funny things possible ;) operator fun unaryMinus(): Zz = -owned
}
