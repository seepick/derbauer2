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

    private val unseenHappenings = mutableListOf<Happening>()
    fun currentHappening() = unseenHappenings.first()

    fun continueNextOrFinish() {
        unseenHappenings.removeAt(0)
        if(unseenHappenings.isEmpty()) {
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
        unseenHappenings.addAll(happenings)
        currentHappening().execute(user)
    }
}
