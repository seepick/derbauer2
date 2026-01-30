package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.features.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.features.TradeLandFeature
import com.github.seepick.derbauer2.game.technology.TechnologyFeature
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.MultiViewSubPage
import com.github.seepick.derbauer2.textengine.Textmap
import kotlin.reflect.KClass

fun <F : Feature> User.hasFeature(featureClass: KClass<F>): Boolean =
    all.findOrNull(featureClass) != null

fun User.hasFeature(descriptor: FeatureDescriptor): Boolean =
    all.filterIsInstance<Feature>().any { it.descriptor == descriptor }

interface FeatureData {
    val label: String
    val asciiArt: AsciiArt
    val multilineDescription: String
}

abstract class Feature(
    val descriptor: FeatureDescriptor,
) : Entity, FeatureData by descriptor {
    override val labelSingular = descriptor.label

    abstract val discriminator: Discriminator<out Feature>

    /**
     * Allows for exhaustive when, although Feature itself is not sealed.
     *
     *     val feature: Feature = TODO()
     *     when(val discriminator = feature.discriminator) {
     *         is Feature.Discriminator.Technology -> discriminator.asRuntimeType { tech: TechnologyFeature ->
     *         }
     *         is Feature.Discriminator.TradeLand -> discriminator.asRuntimeType { tech: TradeLandFeature ->
     *         }
     *     }
     */
    sealed class Discriminator<F : Feature>(private val feature: F) {
        fun <T> asRuntimeType(code: (F) -> T) = code(feature)
        class TradeLand(feature: TradeLandFeature) : Discriminator<TradeLandFeature>(feature)
        class Technology(feature: TechnologyFeature) : Discriminator<TechnologyFeature>(feature)
    }
}

class FeatureInfo(private val feature: Feature) : MultiViewSubPage {
    override val asciiArt = feature.asciiArt

    override fun render(textmap: Textmap) {
        textmap.line(">> ${feature.label} <<")
        textmap.emptyLine()
        textmap.multiLine(feature.multilineDescription)
    }

    override fun execute(user: User) {
        user.enable(feature)
    }
}

class FeatureTurner(private val user: User) {
    fun buildFeaturMultiPages(): List<FeatureInfo> =
        FeatureDescriptor.all
            .filter { !user.hasFeature(it) && it.check(user) }
            .map { FeatureInfo(it.build()) }
}
