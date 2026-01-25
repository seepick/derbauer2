package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.textengine.CurrentPage
import io.github.oshai.kotlinlogging.KotlinLogging

class HappeningController(
    private val user: User,
    private val currentPage: CurrentPage,
) {
    private val log = KotlinLogging.logger {}

    private val happenings = mutableListOf<Happening>()
    fun currentHappening() = happenings.first()

    fun continueNextOrFinish() {
        happenings.removeAt(0)
        if(happenings.isEmpty()) {
            log.debug { "No happenings left, go home." }
            currentPage.page = HomePage::class
        } else {
            log.debug { "Show next happening." }
            currentHappening().execute(user)
        }
    }

    fun process(happenings: List<Happening>) {
        require(happenings.isNotEmpty())
        log.debug { "Processing ${happenings.size} happenings" }
        this.happenings.addAll(happenings)
        currentHappening().execute(user)
    }
}
