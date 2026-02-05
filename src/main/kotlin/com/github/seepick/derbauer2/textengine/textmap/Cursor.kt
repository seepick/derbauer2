package com.github.seepick.derbauer2.textengine.textmap

@Suppress("VariableMinLength")
class Cursor {

    var x = 0
    var y = 0

    fun nextLine() {
        x = 0; y++
    }

    fun reset() {
        x = 0; y = 0
    }
}
