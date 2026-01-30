package com.github.seepick.derbauer2.game.integrationTests

import io.cucumber.java.Before
import io.cucumber.java.BeforeAll
import io.github.oshai.kotlinlogging.KotlinLogging

/** Must use Cucumber's annotations (not the one from JUnit!) */
object TestHook {

    private val log = KotlinLogging.logger {}

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
        log.debug { "${Thread.currentThread().name} - before all" }
    }

    @Before
    @JvmStatic
    fun before() {
        log.debug { "${Thread.currentThread().name} - before" }
    }
}