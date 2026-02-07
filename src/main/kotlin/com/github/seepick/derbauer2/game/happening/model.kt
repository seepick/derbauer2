package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.Probs
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

interface HappeningDescriptor : HappeningData {
    /** technically able to: entities existing, minimum resources owned */
    fun canHappen(user: User, probs: Probs): Boolean
    /** wants to based on probability&co */
    fun willHappen(user: User, probs: Probs): Boolean
    /** precondition(willHappen(user) == true) */
    fun buildHappening(user: User): Happening
    fun initProb(probs: Probs, user: User)

    companion object // for extensions
}
