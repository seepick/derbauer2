package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

//class NewFeatureHappening : Happening
class HappeningPage(
    private val controller: HappeningController,
) : Page {
    private val continueKey = KeyPressed.Command.Space

    override fun renderText(textmap: Textmap) {
        textmap.printLine("⭐️ Happening ⭐️")
        textmap.printEmptyLine()
        controller.currentHappening().render(textmap) // TODO vertical center alignment
        textmap.fillVertical(1)
        textmap.printLine("${continueKey.label} to continue")
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if (key == continueKey) {
            controller.continueNextOrFinish()
            return true
        }
        return false
    }

}