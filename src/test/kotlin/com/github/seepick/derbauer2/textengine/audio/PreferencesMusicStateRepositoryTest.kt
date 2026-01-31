package com.github.seepick.derbauer2.textengine.audio

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.prefs.Preferences

class PreferencesMusicStateRepositoryTest : StringSpec({

    lateinit var repository: PreferencesMusicStateRepository
    lateinit var prefs: Preferences

    beforeEach {
        repository = PreferencesMusicStateRepository()
        prefs = Preferences.userNodeForPackage(PreferencesMusicStateRepository::class.java)
        // Clear any existing state
        prefs.remove("music.isPlaying")
    }

    afterEach {
        // Clean up after tests
        prefs.remove("music.isPlaying")
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

    "When save multiple times Then latest value is returned" {
        repository.savePlayingState(true)
        repository.savePlayingState(false)
        repository.loadPlayingState() shouldBe false

        repository.savePlayingState(true)
        repository.loadPlayingState() shouldBe true
    }
})
