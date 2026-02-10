package com.github.seepick.derbauer2.game.view

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.awt.desktop.QuitHandler

private val log = logger {}

fun attachMacosQuitHandler(onExit: () -> Unit) {
    if (System.getProperty("os.name") != "Mac OS X") {
        log.debug { "Not attaching quit handler (only supported under MacOS but running '${System.getProperty("os.name")}')" }
        return
    }

    log.trace { "Attaching MacOS quit handler reflectively." }
    val awtApplication = Class.forName("com.apple.eawt.Application")
    val setQuitHandler = awtApplication.methods.first { it.name == "setQuitHandler" }
    val application = try {
        awtApplication.methods.first { it.name == "getApplication" }.invoke(null)
    } catch (e: IllegalAccessException) {
        throw MacOsException(
            "Ensure passed the following to the JVM: --add-exports java.desktop/com.apple.eawt=ALL-UNNAMED", e
        )
    }
    setQuitHandler.invoke(application, QuitHandler { _, response ->
        log.info { "Quit invoked." }
        onExit()
        response.performQuit()
    })
}

class MacOsException(message: String, cause: Exception? = null) : Exception(message, cause)
