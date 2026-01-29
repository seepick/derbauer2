package com.github.seepick.derbauer2.itest.steps

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.itest.support.TestWorld
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

/**
 * Step definitions for resource management scenarios.
 * Uses lambda-style step definitions via io.cucumber.java8.En interface.
 */
class ResourceSteps : En {
    
    private var lastTxResult: TxResult? = null
    
    init {
        Given("a user with {int} gold") { goldAmount: Int ->
            TestWorld.userBuilder.setResource(Gold::class, goldAmount.z)
        }
        
        Given("a user with {int} gold and {int} land") { goldAmount: Int, landAmount: Int ->
            TestWorld.userBuilder
                .setResource(Gold::class, goldAmount.z)
                .setResource(Land::class, landAmount.z)
        }
        
        Given("a user with {int} gold, {int} land, and {int} citizens") { 
            goldAmount: Int, landAmount: Int, citizenAmount: Int ->
            TestWorld.userBuilder
                .setResource(Gold::class, goldAmount.z)
                .setResource(Land::class, landAmount.z)
                .setResource(Citizen::class, citizenAmount.z)
        }
        
        Given("a user with {int} gold, {int} land, {int} citizens, and {int} food") {
            goldAmount: Int, landAmount: Int, citizenAmount: Int, foodAmount: Int ->
            TestWorld.userBuilder
                .setResource(Gold::class, goldAmount.z)
                .setResource(Land::class, landAmount.z)
                .setResource(Citizen::class, citizenAmount.z)
                .setResource(Food::class, foodAmount.z)
        }
        
        When("the user spends {int} gold") { goldAmount: Int ->
            lastTxResult = TestWorld.txExecutor.executeResourceTransaction(Gold::class, (-goldAmount).z)
        }
        
        When("the user gains {int} gold") { goldAmount: Int ->
            lastTxResult = TestWorld.txExecutor.executeResourceTransaction(Gold::class, goldAmount.z)
        }
        
        When("the user trades {int} gold for {int} land") { goldAmount: Int, landAmount: Int ->
            lastTxResult = TestWorld.txExecutor.executeMultipleTransactions(
                Gold::class to (-goldAmount).z,
                Land::class to landAmount.z
            )
        }
        
        When("the user tries to spend {int} gold") { goldAmount: Int ->
            lastTxResult = TestWorld.txExecutor.executeResourceTransaction(Gold::class, (-goldAmount).z)
        }
        
        Then("the user should have {int} gold") { expectedGold: Int ->
            val actualGold = TestWorld.userBuilder.getResource(Gold::class)
            assertEquals(expectedGold.z, actualGold, "Gold amount mismatch")
        }
        
        Then("the user should have {int} gold and {int} land") { expectedGold: Int, expectedLand: Int ->
            val actualGold = TestWorld.userBuilder.getResource(Gold::class)
            val actualLand = TestWorld.userBuilder.getResource(Land::class)
            assertEquals(expectedGold.z, actualGold, "Gold amount mismatch")
            assertEquals(expectedLand.z, actualLand, "Land amount mismatch")
        }
        
        Then("the user should have {int} citizens") { expectedCitizens: Int ->
            val actualCitizens = TestWorld.userBuilder.getResource(Citizen::class)
            assertEquals(expectedCitizens.z, actualCitizens, "Citizen count mismatch")
        }
        
        Then("the transaction should fail") {
            assertTrue(
                lastTxResult is TxResult.Fail,
                "Expected transaction to fail but it was: $lastTxResult"
            )
        }
        
        Then("the transaction should succeed") {
            assertTrue(
                lastTxResult is TxResult.Success,
                "Expected transaction to succeed but it was: $lastTxResult"
            )
        }
        
        Then("the transaction should fail due to insufficient resources") {
            assertTrue(
                lastTxResult is TxResult.Fail.InsufficientResources,
                "Expected InsufficientResources failure but was: $lastTxResult"
            )
        }
    }
}
