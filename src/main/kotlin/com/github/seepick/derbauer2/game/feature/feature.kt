package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.textengine.Textmap

//FIXME interface Feature

//class FeatureTree { isEnabled(..) }

class FeatureInfo {
    fun render(textmap: Textmap) {
        textmap.printLine("A feature happening occurred!")
    }

     fun execute(user: User) {
        // TODO implement feature happening effect
    }
}

class FeatureTurner {
    fun turn(): List<FeatureInfo> {
        return listOf(FeatureInfo())
//        return emptyList()
    }
}
