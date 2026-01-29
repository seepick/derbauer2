package com.github.seepick.derbauer2.itest.support

import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.initAssets
import com.github.seepick.derbauer2.game.ownedForTest
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.textengine.mainWindowMatrixSize
import com.github.seepick.derbauer2.textengine.textengineModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.reflect.KClass

private val log = logger {}

/**
 * Test driver for programmatic interaction with the application.
 * Provides a high-level API for test scenarios without UI keyboard simulation.
 */
class TestAppDriver {
    private var _user: User? = null
    
    val user: User
        get() = _user ?: error("TestAppDriver not initialized. Call initialize() first.")
    
    /**
     * Initialize the test driver with Koin DI and a fresh user.
     */
    fun initialize() {
        log.info { "Initializing TestAppDriver" }
        try {
            stopKoin()
        } catch (e: Exception) {
            // Koin not started yet, that's fine
        }
        
        startKoin {
            modules(
                textengineModule(DerBauer2.initPageClass, mainWindowMatrixSize),
                gameModule()
            )
        }
        
        _user = org.koin.core.context.GlobalContext.get().get<User>()
        _user!!.initAssets()
    }
    
    /**
     * Clean up resources after test.
     */
    fun cleanup() {
        log.info { "Cleaning up TestAppDriver" }
        _user = null
        try {
            stopKoin()
        } catch (e: Exception) {
            log.warn(e) { "Error stopping Koin" }
        }
    }
}

/**
 * Builder for setting up test user state.
 */
class TestUserBuilder(private val user: User) {
    
    fun setResource(resourceClass: KClass<out Resource>, amount: Z): TestUserBuilder {
        val resource = user.all.find(resourceClass)
        resource.ownedForTest = amount
        log.debug { "Set ${resourceClass.simpleName} to $amount" }
        return this
    }
    
    fun setBuilding(buildingClass: KClass<out Building>, amount: Z): TestUserBuilder {
        val building = user.all.find(buildingClass)
        building.ownedForTest = amount
        log.debug { "Set ${buildingClass.simpleName} to $amount" }
        return this
    }
    
    fun getResource(resourceClass: KClass<out Resource>): Z {
        return user.all.find(resourceClass).owned
    }
    
    fun getBuilding(buildingClass: KClass<out Building>): Z {
        return user.all.find(buildingClass).owned
    }
}

/**
 * Helper for executing domain transactions directly.
 */
class TestTransactionExecutor(private val user: User) {
    
    fun executeResourceTransaction(resourceClass: KClass<out Resource>, amount: Z): TxResult {
        log.debug { "Executing resource transaction: ${resourceClass.simpleName} $amount" }
        return user.execTx(TxResource(resourceClass, amount.asZz))
    }
    
    fun executeBuildingTransaction(buildingClass: KClass<out Building>, amount: Z): TxResult {
        log.debug { "Executing building transaction: ${buildingClass.simpleName} $amount" }
        return user.execTx(TxBuilding(buildingClass, amount.asZz))
    }
    
    fun executeMultipleTransactions(vararg transactions: Pair<KClass<out Asset>, Z>): TxResult {
        val txs = transactions.map { (assetClass, amount) ->
            when {
                Resource::class.java.isAssignableFrom(assetClass.java) -> {
                    @Suppress("UNCHECKED_CAST")
                    TxResource(assetClass as KClass<out Resource>, amount.asZz)
                }
                Building::class.java.isAssignableFrom(assetClass.java) -> {
                    @Suppress("UNCHECKED_CAST")
                    TxBuilding(assetClass as KClass<out Building>, amount.asZz)
                }
                else -> error("Unknown asset type: ${assetClass.simpleName}")
            }
        }
        log.debug { "Executing multiple transactions: ${txs.size} transactions" }
        return user.execTx(txs)
    }
}

private val Z.asZz: com.github.seepick.derbauer2.game.common.Zz
    get() = com.github.seepick.derbauer2.game.common.Zz(this)

