package dev.fruxz.ascend.tool.path

import java.io.File
import java.nio.file.Path

@Deprecated(message = "Use built-in path protocols instead.")
fun interface PathProtocol {

    fun processor(path: Path): File?

}