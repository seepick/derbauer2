package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.Happening
import com.github.seepick.derbauer2.game.happening.HappeningData
import com.github.seepick.derbauer2.game.happening.HappeningNature

interface HappeningDescriptorRepo {
    val all: List<HappeningDescriptor>
}

object DefaultHappeningDescriptorRepo : HappeningDescriptorRepo {
    override val all: List<HappeningDescriptor> = listOf(
        FoundGoldDescriptor,
        RatsEatFoodDescriptor,
        // !!!!!!!!!!!!!!!!!!!!!
        // keep MANUALLY in sync
        // !!!!!!!!!!!!!!!!!!!!!
    )
}

abstract class HappeningDescriptor(
    override val nature: HappeningNature
) : HappeningData {
    abstract fun canHappen(user: User): Boolean
    abstract fun build(user: User): Happening

    companion object {} // for extensions
}
