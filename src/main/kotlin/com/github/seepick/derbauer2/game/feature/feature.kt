package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.logic.Entity
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.MultiViewItem
import com.github.seepick.derbauer2.textengine.Textmap

fun User.hasFeature(searchDescriptor: FeatureDescriptor): Boolean =
    all.filterIsInstance<Feature>().any { it.descriptor == searchDescriptor }

interface FeatureData {
    val name: String
    val asciiArt: AsciiArt
    val description: String
}

sealed class FeatureDescriptor(
    override val name: String,
    override val asciiArt: AsciiArt,
    override val description: String,
) : FeatureData {

    companion object {
        val all by lazy {
            listOf(
                TradeLand,
            )
        }
    }

    abstract fun build(): Feature
    abstract fun check(user: User): Boolean

    object TradeLand : FeatureDescriptor(
        name = "Trade Land",
        asciiArt = AsciiArt.island,
        description = "You can now buy ${Land.Text.emojiAndLabel} for some other stuff.\nAnd some more... hehe 😅",
    ) {
        override fun check(user: User) = user.landAvailable <= 2
        override fun build() = Feature.TradeLandFeature(this)
    }
}

sealed class Feature(
    val descriptor: FeatureDescriptor,
) : Entity, FeatureData by descriptor {
    class TradeLandFeature(descriptor: FeatureDescriptor.TradeLand) : Feature(descriptor)
}

class FeatureInfo(private val feature: Feature) : MultiViewItem {
    override fun render(textmap: Textmap) {
        textmap.line(">> ${feature.name} <<")
        textmap.emptyLine()
        textmap.multiLine(feature.description)
    }

    override val asciiArt = feature.asciiArt

    override fun execute(user: User) {
        user.add(feature)
    }
}

class FeatureTurner(private val user: User) {
    fun buildFeaturMultiPages(): List<FeatureInfo> =
        FeatureDescriptor.all
            .filter { !user.hasFeature(it) && it.check(user) }
            .map { FeatureInfo(it.build()) }
}
