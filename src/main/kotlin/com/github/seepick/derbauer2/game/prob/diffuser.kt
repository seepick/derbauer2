package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz

@JvmInline
value class ProbDiffuserKey(val name: String) {
    override fun toString() = "${this::class.simpleName}[$name]"

    companion object
}

interface ProbDiffuser {
    fun diffuse(baseValue: Zz): Zz
}

class GaussianDiffuser() : ProbDiffuser {
    override fun diffuse(baseValue: Zz): Zz {
        return (baseValue.toDouble() + Math.random() * 10).toLong().zz // FIXME implement me
    }
}

object PassThroughDiffuser : ProbDiffuser {
    override fun diffuse(baseValue: Zz) = baseValue
}

class StaticDiffuser(private val staticValue: Zz) : ProbDiffuser {
    override fun diffuse(baseValue: Zz) = staticValue
}

data class ProbDiffuserHandle(
    val key: ProbDiffuserKey,
    val diffuser: ProbDiffuser,
) {
    fun withSelector(newDiffuser: ProbDiffuser) = ProbDiffuserHandle(key, newDiffuser)
}
