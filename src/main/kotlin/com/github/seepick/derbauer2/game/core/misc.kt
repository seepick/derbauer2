package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.common.ClasspathScanner
import com.github.seepick.derbauer2.game.Derbauer2Root

enum class DerBauer2SysProp(val key: String) {
    DISABLE_MACOS_QUIT_HANDLER("derbauer2.disableMacOsQuitHandler"),
    DEV_MODE("derbauer2.devMode"),
}

private val globalClasspathScanner = ClasspathScanner(Derbauer2Root.packageName)
val ClasspathScanner.Companion.global get() = globalClasspathScanner
