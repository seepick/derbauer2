package com.github.seepick.derbauer2.game.common

import java.nio.file.FileSystemNotFoundException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun ifDo(condition: Boolean, execution: () -> Unit): Boolean {
    contract {
        returns(true) implies condition
        callsInPlace(execution, InvocationKind.AT_MOST_ONCE)
    }
    if (condition) execution()
    return condition
}

fun loadFilesFromClasspath(folderPath: String): List<String> {
    val normalized = folderPath.removePrefix("/").trimEnd('/')
    val loader = Thread.currentThread().contextClassLoader
    val resource = loader.getResource(normalized) ?: return emptyList()
    val uri = resource.toURI()
    return when (uri.scheme) {
        "file" -> {
            val dir = Paths.get(uri)
            Files.list(dir).use { stream ->
                stream.iterator().asSequence().map { "/$normalized/${it.fileName}" }.toList()
            }
        }

        "jar" -> {
            val fs = try {
                FileSystems.getFileSystem(uri)
            } catch (e: FileSystemNotFoundException) {
                FileSystems.newFileSystem(uri, emptyMap<String, Any>())
            }
            val pathInJar = fs.getPath("/$normalized")
            Files.list(pathInJar).use { stream ->
                stream.iterator().asSequence().map { "/$normalized/${it.fileName}" }.toList()
            }
        }

        else -> emptyList()
    }
}
