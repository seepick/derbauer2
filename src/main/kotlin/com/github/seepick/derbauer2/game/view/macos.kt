package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.DerBauer2SysProp
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.awt.desktop.QuitHandler

private val log = logger {}


fun attachMacosQuitHandler(onExit: () -> Unit) {
    if (System.getProperty("os.name") != "Mac OS X") {
        log.debug {
            "Not attaching quit handler (running non-macos system: '${System.getProperty("os.name")}')"
        }
        return
    }
    if (System.getProperty(DerBauer2SysProp.DISABLE_MACOS_QUIT_HANDLER.key) != null) {
        log.debug {
            "Not attaching quit handler (disabled via system property '${DerBauer2SysProp.DISABLE_MACOS_QUIT_HANDLER}')"
        }
        return
    }
    reallyAttach(onExit)
}

private fun reallyAttach(onExit: () -> Unit) {
    log.trace { "Attaching MacOS quit handler." }
    val awtApplication = Class.forName("com.apple.eawt.Application")
    val application = try {
        awtApplication.methods.first { it.name == "getApplication" }.invoke(null)
    } catch (e: IllegalAccessException) {
        throw AppleAwtException(
            "Ensure passed the following to the JVM: --add-exports java.desktop/com.apple.eawt=ALL-UNNAMED", e
        )
    }
    val setQuitHandler = awtApplication.methods.first { it.name == "setQuitHandler" }
    setQuitHandler.invoke(application, QuitHandler { _, response ->
        log.info { "Quit invoked." }
        onExit()
        response.performQuit()
    })
}

class AppleAwtException(message: String, cause: Exception? = null) : Exception(message, cause)
