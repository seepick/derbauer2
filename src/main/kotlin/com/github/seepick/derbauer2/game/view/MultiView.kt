package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Renderer
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface MultiViewSubPage : Renderer {
    val asciiArt: AsciiArt
    fun execute(user: User)
}

abstract class MultiView<PAGE : MultiViewSubPage>(
    private val user: User,
    private val currentPage: CurrentPage,
    private val targetPageClass: KClass<out Page>
) {
    private val log = logger {}
    private var onFinishedProcessing: () -> Unit = {}
    private val unseen = mutableListOf<PAGE>()
    fun current() = unseen.first()

    fun process(toBeProcessed: List<PAGE>, onFinishedProcessing: () -> Unit) {
        if (toBeProcessed.isEmpty()) {
            onFinishedProcessing()
            return
        }
        currentPage.pageClass = targetPageClass
        this.onFinishedProcessing = onFinishedProcessing
        log.debug { "Processing ${toBeProcessed.size}" }
        unseen.addAll(toBeProcessed)
        current().execute(user)
    }

    fun continueNextOrFinish() {
        unseen.removeAt(0)
        if (unseen.isEmpty()) {
            log.debug { "Done; no more left." }
            onFinishedProcessing()
        } else {
            log.debug { "Showing next." }
            current().execute(user)
        }
    }
}
