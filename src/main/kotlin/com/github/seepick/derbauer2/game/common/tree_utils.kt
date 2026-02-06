package com.github.seepick.derbauer2.game.common

import com.github.seepick.derbauer2.game.core.GeneratedByAI

@GeneratedByAI
fun <T> validCycleFree(all: List<T>, adjacency: Map<T, List<T>>) {
    val visiting = mutableSetOf<T>()
    val visited = mutableSetOf<T>()
    fun dfs(node: T) {
        if (node in visited) {
            return
        }
        require(node !in visiting) { "Cycle detected for: $node" }
        visiting += node
        adjacency[node]?.forEach { dfs(it) }
        visiting -= node
        visited += node
    }
    all.forEach { dfs(it) }
}

@GeneratedByAI
@Suppress("LongParameterList")
fun <T> printTree(
    header: String,
    roots: List<T>,
    children: Map<T, List<T>>,
    isChecked: (T) -> Boolean,
    label: T.() -> String,
): String {
    val sb = StringBuilder()
    sb.append("$header\n")
    fun render(node: T, prefix: String, isLast: Boolean) {
        sb.append(prefix)
        sb.append(if (isLast) "└── " else "├── ")
        sb.append(node.label())
        if (isChecked(node)) {
            sb.append(" ✅")
        }
        sb.append("\n")
        val kids = children[node] ?: emptyList()
        kids.forEachIndexed { idx, kid ->
            val last = idx == kids.size - 1
            val newPrefix = prefix + if (isLast) "    " else "│   "
            render(kid, newPrefix, last)
        }
    }

    roots.forEachIndexed { idx, root ->
        render(root, "", idx == roots.size - 1)
    }
    return sb.toString().trim()
}
