package com.github.seepick.derbauer2.itest.steps

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.itest.support.TestWorld
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

/**
 * Step definitions for building construction scenarios.
 */
class BuildingSteps : En {
    
    private var lastTxResult: TxResult? = null
    
    init {
        Given("a user with {int} houses") { houseCount: Int ->
            TestWorld.userBuilder.setBuilding(House::class, houseCount.z)
        }
        
        Given("a user with {int} gold and {int} houses") { goldAmount: Int, houseCount: Int ->
            TestWorld.userBuilder
                .setResource(Gold::class, goldAmount.z)
                .setBuilding(House::class, houseCount.z)
        }
        
        Given("a user with {int} farms and {int} granaries") { farmCount: Int, granaryCount: Int ->
            TestWorld.userBuilder
                .setBuilding(Farm::class, farmCount.z)
                .setBuilding(Granary::class, granaryCount.z)
        }
        
        When("the user builds {int} house(s)") { houseCount: Int ->
            lastTxResult = TestWorld.txExecutor.executeBuildingTransaction(House::class, houseCount.z)
        }
        
        When("the user builds {int} farm(s)") { farmCount: Int ->
            lastTxResult = TestWorld.txExecutor.executeBuildingTransaction(Farm::class, farmCount.z)
        }
        
        When("the user builds {int} granary/granaries") { granaryCount: Int ->
            lastTxResult = TestWorld.txExecutor.executeBuildingTransaction(Granary::class, granaryCount.z)
        }
        
        When("the user tries to build {int} house(s)") { houseCount: Int ->
            lastTxResult = TestWorld.txExecutor.executeBuildingTransaction(House::class, houseCount.z)
        }
        
        When("the user demolishes {int} house(s)") { houseCount: Int ->
            lastTxResult = TestWorld.txExecutor.executeBuildingTransaction(House::class, (-houseCount).z)
        }
        
        Then("the user should have {int} house(s)") { expectedHouses: Int ->
            val actualHouses = TestWorld.userBuilder.getBuilding(House::class)
            assertEquals(expectedHouses.z, actualHouses, "House count mismatch")
        }
        
        Then("the user should have {int} farm(s)") { expectedFarms: Int ->
            val actualFarms = TestWorld.userBuilder.getBuilding(Farm::class)
            assertEquals(expectedFarms.z, actualFarms, "Farm count mismatch")
        }
        
        Then("the user should have {int} granary/granaries") { expectedGranaries: Int ->
            val actualGranaries = TestWorld.userBuilder.getBuilding(Granary::class)
            assertEquals(expectedGranaries.z, actualGranaries, "Granary count mismatch")
        }
        
        Then("the user should have {int} house(s) and {int} farm(s)") { 
            expectedHouses: Int, expectedFarms: Int ->
            val actualHouses = TestWorld.userBuilder.getBuilding(House::class)
            val actualFarms = TestWorld.userBuilder.getBuilding(Farm::class)
            assertEquals(expectedHouses.z, actualHouses, "House count mismatch")
            assertEquals(expectedFarms.z, actualFarms, "Farm count mismatch")
        }
        
        Then("the building transaction should fail") {
            assertTrue(
                lastTxResult is TxResult.Fail,
                "Expected building transaction to fail but it was: $lastTxResult"
            )
        }
        
        Then("the building transaction should succeed") {
            assertTrue(
                lastTxResult is TxResult.Success,
                "Expected building transaction to succeed but it was: $lastTxResult"
            )
        }
    }
}
