package com.github.seepick.derbauer2.game.core

import java.util.Properties

data class AppProperties(
    val version: String,
) {
    companion object {
        private const val FILE_PATH = "/app.properties"
        private const val KEY_VERSION = "version"
        val instance by lazy {
            val properties = Properties()
            val stream = AppProperties::class.java.getResourceAsStream(FILE_PATH)
                ?: error("Properties file does not exist at: $FILE_PATH")
            stream.use { properties.load(it) }
            AppProperties(
                version = properties.getProperty(KEY_VERSION)
                    ?: error("Version with key [$KEY_VERSION] is missing in: $FILE_PATH"),
            )
        }
    }
}
