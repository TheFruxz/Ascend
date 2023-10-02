package dev.fruxz.ascend.tool.path

import dev.fruxz.ascend.annotation.RefactoringCandidate
import java.io.File
import java.nio.file.Path

@RefactoringCandidate
object PathProcessor {

    val protocols: MutableMap<String, PathProtocol> = mutableMapOf()

    @Throws(IllegalArgumentException::class)
    fun register(name: String, protocol: PathProtocol) {
        if (protocols.containsKey(name)) throw IllegalArgumentException("Protocol with name $name is already registered!")

        protocols[name] = protocol

    }

    fun unregister(name: String) = protocols.remove(name)

    fun getProtocol(name: String) = protocols[name]

    @Throws(NoSuchElementException::class)
    fun getFile(path: String): File? {
        val triggerWord = path.split("://").firstOrNull()
        val protocol = protocols[triggerWord] ?: throw NoSuchElementException("Protocol with name $triggerWord is not registered!")

        return protocol::processor.invoke(Path.of(path.removePrefix("$triggerWord://")))
    }

}