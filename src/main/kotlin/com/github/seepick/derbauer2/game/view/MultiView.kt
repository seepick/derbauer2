package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface MultiViewItem {
    fun render(textmap: Textmap)
    fun execute(user: User)
}

abstract class MultiView<ITEM : MultiViewItem>(
    private val user: User,
    private val currentPage: CurrentPage,
    private val targetPageClass: KClass<out Page>
) {
    private val log = logger {}
    private val unseen = mutableListOf<ITEM>()
    private var onFinishedProcessing: () -> Unit = {}

    fun current() = unseen.first()

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

    fun process(toBeProcessed: List<ITEM>, onFinishedProcessing: () -> Unit) {
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
}
