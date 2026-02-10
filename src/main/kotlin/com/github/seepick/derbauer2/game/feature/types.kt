package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

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
    override fun mutate(user: User) {}
    override fun deepCopy() = this // immutable by default
    override fun toString() = "${this::class.simpleName}($label)"
}

abstract class FeatureRef(
    final override val label: String,
    final override val asciiArt: AsciiArt,
    final override val multilineDescription: String,
    private val checkIt: (user: User, reports: Reports) -> Boolean,
    private val buildIt: () -> Feature,
) : FeatureData {
    fun check(user: User, reports: Reports) = checkIt(user, reports)
    fun build() = buildIt()
}
