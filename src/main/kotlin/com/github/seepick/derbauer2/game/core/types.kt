package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import kotlin.math.abs
import kotlin.reflect.KClass

interface Entity : DeepCopyable<Entity>, HasLabels

/** Needed to validate transactions; create a copy/snapshot, apply TXs, and then validate. */
fun interface DeepCopyable<T> {
    fun deepCopy(): T
}

/**
 * A physical object (house, resource, people); not an abstract concept (tech).
 * PS: itest assumes that all Assets have a zero-argument constructor; see GivenDsl#createAssetInstance
 */
interface Asset : Entity, Ownable

interface Ownable : Entity {
    val owned: Z get() = _setOwnedInternal
    @Suppress("PropertyName", "VariableNaming")
    var _setOwnedInternal: Z
}

val <T> T.emojiAndOwned: String where T : Entity, T : Ownable get() = "${emojiSpaceOrEmpty}${owned}"

interface OwnableReference {
    val ownableClass: KClass<out Ownable>
}

interface HasLabels {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    fun labelFor(unsignedAmount: Z) = if (unsignedAmount == 1.z) labelSingular else labelPlural
    fun labelFor(signedAmount: Zz) = if (abs(signedAmount.value) == 1L) labelSingular else labelPlural
}
