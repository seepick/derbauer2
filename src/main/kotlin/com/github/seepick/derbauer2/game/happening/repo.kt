package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.happening.HappeningType.entries

interface HappeningDescriptorRepo {
    fun getAllDescriptors(): List<HappeningDescriptor>
}

object DefaultHappeningDescriptorRepo : HappeningDescriptorRepo {

    // must be lazy, as entries are not initialized at object creation time :-/
    private val allDescriptors by lazy {
        entries.map { it.descriptor }
    }

    override fun getAllDescriptors() = allDescriptors
}
