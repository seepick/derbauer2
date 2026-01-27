package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.features.FeatureDescriptor
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.MultiViewSubPage
import com.github.seepick.derbauer2.textengine.Textmap
import kotlin.reflect.KClass

// ... not sure yet which style i prefer?
//inline fun <reified F: Feature> User.hasFeature(): Boolean =
//    all.findOrNull<F>() != null

fun <F: Feature> User.hasFeature(featureClass: KClass<F>): Boolean =
    all.findOrNull(featureClass) != null

fun User.hasFeature(descriptor: FeatureDescriptor): Boolean =
    all.filterIsInstance<Feature>().any { it.descriptor == descriptor }

interface FeatureData {
    val label: String
    val asciiArt: AsciiArt
    val description: String
}

abstract class Feature(
    val descriptor: FeatureDescriptor,
) : Entity, FeatureData by descriptor {
    override val labelSingular = descriptor.label
}

class FeatureInfo(private val feature: Feature) : MultiViewSubPage {
    override val asciiArt = feature.asciiArt

    override fun render(textmap: Textmap) {
        textmap.line(">> ${feature.label} <<")
        textmap.emptyLine()
        textmap.multiLine(feature.description)
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
