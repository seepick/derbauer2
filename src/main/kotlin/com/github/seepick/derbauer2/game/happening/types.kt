package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.common.emoji.emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.view.MultiViewSubPage

enum class HappeningNature(val emoji: Emoji) {
    Positive("✨".emoji),
    Negative("⚠️".emoji),
    Mixed("⚡".emoji),
    Neutral("🔹".emoji),
}

interface HappeningData {
    val nature: HappeningNature
}

interface Happening : MultiViewSubPage, HappeningData

interface HappeningRef : HappeningData {
    /** technically able to: entities existing, minimum resources owned */
    fun canHappen(user: User, probs: Probs): Boolean = true
    /** wants to based on probability&co */
    fun willHappen(user: User, probs: Probs): Boolean = true
    /** precondition(willHappen(user) == true) */
    fun buildHappening(user: User): Happening
    fun initProb(probs: Probs, user: User, turn: CurrentTurn) {}

    companion object // for extensions
}
