package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User

class TechTree(
    private val user: User,
    private val items: List<TechTreeItem>,
) {
    fun getAvailableToBeResearched(): List<TechTreeItem> =
        items.filter {
            it.state is TechState.Unresearched
            // && all prerequisites researched
        }
}

interface TechTreeItem : TechStaticData {
    fun buildTech(): Tech

    var state: TechState
}

abstract class AbstractTechTreeItem(
    data: TechStaticData,
    private val techBuilder: () -> Tech,
) : TechTreeItem, TechStaticData by data {
    override var state: TechState = TechState.Unresearched
    override fun buildTech() = techBuilder()
}

sealed interface TechState {
    object Unresearched : TechState
    class Researched(val tech: Tech) : TechState
}
