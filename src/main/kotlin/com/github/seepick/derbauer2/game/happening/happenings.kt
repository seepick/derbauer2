package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.RatsEatFoodDescriptor

abstract class HappeningDescriptor(
    override val nature: HappeningNature
) : HappeningData {

    abstract fun canHappen(user: User): Boolean
    abstract fun build(user: User): Happening

    companion object {
        val all: List<HappeningDescriptor> by lazy {
            listOf(
                FoundGoldDescriptor,
                RatsEatFoodDescriptor,
                // !!!!!!!!!!!!!!!!!!!!!
                // keep MANUALLY in sync
                // !!!!!!!!!!!!!!!!!!!!!
            )
        }
    }
}
