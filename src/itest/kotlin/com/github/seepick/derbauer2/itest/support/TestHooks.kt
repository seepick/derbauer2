package com.github.seepick.derbauer2.itest.support

import io.cucumber.java.Before
import io.cucumber.java.After
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

/**
 * Cucumber hooks for managing test lifecycle.
 * Before/After hooks ensure clean state for each scenario.
 */
class TestHooks {
    
    @Before
    fun beforeScenario() {
        log.info { "=== Starting Cucumber scenario ===" }
        TestWorld.reset()
    }
    
    @After
    fun afterScenario() {
        log.info { "=== Ending Cucumber scenario ===" }
        if (TestWorld::driver.isInitialized) {
            TestWorld.driver.cleanup()
        }
    }
}
