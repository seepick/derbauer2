package com.github.seepick.derbauer2.game.core

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.util.prefs.Preferences

class PreferencesStateRepositoryTest : StringSpec({

    lateinit var repository: PreferencesStateRepository
    lateinit var prefs: Preferences

    beforeEach {
        repository = PreferencesStateRepository(PreferencesStateRepositoryTest::class)
        prefs = PreferencesStateRepository.loadPreferences(PreferencesStateRepositoryTest::class)
        prefs.clear()
    }
    afterEach {
        prefs.clear()
    }

    "When load playing state with no saved value Then return null" {
        repository.getMusicPlaying().shouldBeNull()
    }

    "When save playing state as false Then load returns false" {
        repository.setMusicPlaying(false)
        repository.getMusicPlaying() shouldBe false
    }

    "When save playing state as true Then load returns true" {
        repository.setMusicPlaying(true)
        repository.getMusicPlaying() shouldBe true
    }
})
