package com.github.seepick.derbauer2.textengine.audio

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class MusicStateManagerTest : StringSpec({

    "When shouldAutoPlay Then delegates to repository loadPlayingState" {
        val repository = mockk<MusicStateRepository>()
        every { repository.loadPlayingState() } returns true
        
        val manager = MusicStateManager(repository)
        
        manager.shouldAutoPlay() shouldBe true
        verify { repository.loadPlayingState() }
    }

    "When updatePlayingState with true Then saves to repository" {
        val repository = mockk<MusicStateRepository>(relaxed = true)
        val manager = MusicStateManager(repository)
        
        manager.updatePlayingState(true)
        
        verify { repository.savePlayingState(true) }
    }

    "When updatePlayingState with false Then saves to repository" {
        val repository = mockk<MusicStateRepository>(relaxed = true)
        val manager = MusicStateManager(repository)
        
        manager.updatePlayingState(false)
        
        verify { repository.savePlayingState(false) }
    }
})
