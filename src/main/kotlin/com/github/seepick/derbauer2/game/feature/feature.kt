package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt
import kotlin.reflect.KClass

fun <F : Feature> User.hasFeature(featureClass: KClass<F>): Boolean =
    all.findOrNull(featureClass) != null

inline fun <reified F : Feature> User.hasFeature(): Boolean =
    hasFeature(F::class)

fun User.hasFeature(ref: FeatureRef): Boolean =
    all.filterIsInstance<Feature>().any { it.ref == ref }

interface FeatureData : HasLabel {
    val asciiArt: AsciiArt
    val multilineDescription: String
}

interface Feature : Entity, FeatureData {
    val ref: FeatureRef
    fun mutate(user: User)
}

abstract class FeatureImpl(
    override val ref: FeatureRef,
) : Feature, FeatureData by ref {
    override fun toString() = "${this::class.simpleName}($label)"
}

abstract class FeatureRef(
    final override val label: String,
    final override val asciiArt: AsciiArt,
    final override val multilineDescription: String,
) : FeatureData {

    @Suppress("unused")
    abstract fun check(user: User, reports: Reports): Boolean
    abstract fun build(): Feature
}
