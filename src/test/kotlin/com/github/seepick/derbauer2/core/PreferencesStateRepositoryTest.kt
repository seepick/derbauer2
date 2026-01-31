package com.github.seepick.derbauer2.core

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.prefs.Preferences

class PreferencesStateRepositoryTest : StringSpec({

    lateinit var repository: PreferencesStateRepository
    lateinit var prefs: Preferences

    beforeEach {
        repository = PreferencesStateRepository(PreferencesStateRepositoryTest::class)
        prefs = Preferences.userNodeForPackage(PreferencesStateRepositoryTest::class.java)
        prefs.remove(PREF_KEY)
    }

    afterEach {
        prefs.remove(PREF_KEY)
    }

    "When load playing state with no saved value Then return default true" {
        repository.loadPlayingState() shouldBe true
    }

    "When save playing state as false Then load returns false" {
        repository.savePlayingState(false)
        repository.loadPlayingState() shouldBe false
    }

    "When save playing state as true Then load returns true" {
        repository.savePlayingState(true)
        repository.loadPlayingState() shouldBe true
    }
}) {
    companion object {
        private const val PREF_KEY = "music.isPlaying"
    }
}
