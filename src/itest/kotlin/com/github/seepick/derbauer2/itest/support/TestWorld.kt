package com.github.seepick.derbauer2.itest.support

import com.github.seepick.derbauer2.game.transaction.TxResult

/**
 * Shared state container for Cucumber scenarios.
 * This holds the test driver and other shared state across steps.
 */
object TestWorld {
    lateinit var driver: TestAppDriver
    lateinit var userBuilder: TestUserBuilder
    lateinit var txExecutor: TestTransactionExecutor
    var lastTxResult: TxResult? = null
    
    fun reset() {
        if (::driver.isInitialized) {
            driver.cleanup()
        }
        driver = TestAppDriver()
        driver.initialize()
        userBuilder = TestUserBuilder(driver.user)
        txExecutor = TestTransactionExecutor(driver.user)
        lastTxResult = null
    }
}
