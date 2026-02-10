package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabels
import com.github.seepick.derbauer2.game.view.ViewOrder

interface Resource : Asset, HasLabels, HasEmoji, ViewOrder

interface StorableResource : Resource
