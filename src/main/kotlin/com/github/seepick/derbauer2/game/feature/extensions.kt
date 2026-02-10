package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

fun <F : Feature> User.hasFeature(featureClass: KClass<F>): Boolean =
    all.findOrNull(featureClass) != null

inline fun <reified F : Feature> User.hasFeature(): Boolean =
    hasFeature(F::class)

fun User.hasFeature(ref: FeatureRef): Boolean =
    all.filterIsInstance<Feature>().any { it.ref == ref }
