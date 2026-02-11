package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.common.ClasspathScanner
import com.github.seepick.derbauer2.game.core.global

fun interface TechRefRegistry {
    fun getAll(): List<TechRef>
}

object ClasspathScanTechRefRegistry : TechRefRegistry {
    private val techRefs by lazy {
        ClasspathScanner.global.scanObjectsImplementing(TechRef::class)
    }

    override fun getAll() = techRefs
}
