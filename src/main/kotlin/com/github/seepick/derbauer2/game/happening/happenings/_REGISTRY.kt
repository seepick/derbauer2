package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.common.ClasspathScanner
import com.github.seepick.derbauer2.game.core.global
import com.github.seepick.derbauer2.game.happening.HappeningRef

interface HappeningRefRegistry {
    /** @return is never empty */
    fun getAll(): List<HappeningRef>

    companion object
}

object DefaultHappeningRefRegistry : HappeningRefRegistry {
    private val happeningRefs by lazy {
        ClasspathScanner.global.scanObjectsImplementing(HappeningRef::class)
    }

    override fun getAll() = happeningRefs
}
