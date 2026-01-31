package com.github.seepick.derbauer2.textengine.audio

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.prefs.Preferences

class PreferencesMusicStateRepositoryTest : StringSpec({

    lateinit var repository: PreferencesMusicStateRepository
    lateinit var prefs: Preferences

    beforeEach {
        repository = PreferencesMusicStateRepository(PreferencesMusicStateRepositoryTest::class)
        prefs = Preferences.userNodeForPackage(PreferencesMusicStateRepositoryTest::class.java)
        prefs.remove("music.isPlaying")
    }

    afterEach {
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
})
