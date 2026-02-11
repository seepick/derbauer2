package com.github.seepick.derbauer2.textengine.audio

import com.github.seepick.derbauer2.game.building.BuildingBuiltAction
import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.core.ActionBusListener
import com.github.seepick.derbauer2.game.tech.TechResearchedAction
import com.github.seepick.derbauer2.game.trade.ResourcesTradedAction

class SoundPlayService(
    private val realPlayer: SoundPlayer,
) : ActionBusListener, SoundPlayer {

    private var currentPlayer = realPlayer

    // FIXME at startup, read and set from java prefs (memory)
    fun enable(isEnabled: Boolean) {
        currentPlayer = if (isEnabled) realPlayer else NoopSoundPlayer
    }

    override fun onAction(action: Action) {
        action.toSound()?.also {
            play(it)
        }
    }

    override fun play(sound: SoundFile) {
        currentPlayer.play(sound)
    }
}

private val actionClassToSoundFile = mapOf(
    BuildingBuiltAction::class to SoundFile("/sounds/buildingBuilt.mp3"),
    ResourcesTradedAction::class to SoundFile("/sounds/resourcesTraded.mp3"),
    TechResearchedAction::class to SoundFile("/sounds/techResearched.mp3"),
)

private fun Action.toSound(): SoundFile? =
    actionClassToSoundFile[this::class]
