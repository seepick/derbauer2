package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.stat.HappinessFeature
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.trading.FoodMerchantFeature
import com.github.seepick.derbauer2.game.trading.TradeLandFeature
import com.github.seepick.derbauer2.game.trading.TradingFeature
import com.github.seepick.derbauer2.game.view.AsciiArt
import kotlin.reflect.KClass

fun <F : Feature> User.hasFeature(featureClass: KClass<F>): Boolean =
    all.findOrNull(featureClass) != null

inline fun <reified F : Feature> User.hasFeature(): Boolean =
    hasFeature(F::class)

fun User.hasFeature(ref: FeatureRef): Boolean =
    all.filterIsInstance<Feature>().any { it.ref == ref }

interface FeatureData {
    val label: String
    val asciiArt: AsciiArt
    val multilineDescription: String
}

abstract class Feature(
    val ref: FeatureRef,
) : Entity, FeatureData by ref {
    override val labelSingular = ref.label

    abstract val discriminator: Discriminator<out Feature>

    override fun toString() = "${this::class.simpleName}($label)"
    abstract fun mutate(user: User)

    /**
     * Allows for exhaustive when, although Feature itself is not sealed.
     *
     *     val feature: Feature = getSomeFeature()
     *     when(val discriminator = feature.discriminator) {
     *         is Feature.Discriminator.Technology -> discriminator.asRuntimeType { tech: TechnologyFeature ->
     *         }
     *         is Feature.Discriminator.TradeLand -> discriminator.asRuntimeType { tech: TradeLandFeature ->
     *         }
     *     }
     */
    sealed class Discriminator<F : Feature>(private val feature: F) {
        fun <T> asRuntimeType(code: (F) -> T) = code(feature)
        class Trading(feature: TradingFeature) : Discriminator<TradingFeature>(feature)
        class TradeLand(feature: TradeLandFeature) : Discriminator<TradeLandFeature>(feature)
        class FoodMerchant(feature: FoodMerchantFeature) : Discriminator<FoodMerchantFeature>(feature)
        class Technology(feature: TechnologyFeature) : Discriminator<TechnologyFeature>(feature)
        class Happiness(feature: HappinessFeature) : Discriminator<HappinessFeature>(feature)
    }
}
