package com.github.seepick.derbauer2.game.view

import java.util.concurrent.atomic.AtomicInteger

interface ViewOrder {
    val viewOrder: Int

    object ResourceOrder {
        private val counter = AtomicInteger()

        val Gold = counter.incrementAndGet()
        val Food = counter.incrementAndGet()
        val Land = counter.incrementAndGet()
        val Citizen = counter.incrementAndGet()

        val Knowledge = counter.incrementAndGet()
    }
}
