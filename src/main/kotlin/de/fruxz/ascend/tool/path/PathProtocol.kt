package de.fruxz.ascend.tool.path

import java.io.File
import java.nio.file.Path

fun interface PathProtocol {

    fun processor(path: Path): File?

}