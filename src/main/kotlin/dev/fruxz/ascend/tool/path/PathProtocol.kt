package dev.fruxz.ascend.tool.path

import dev.fruxz.ascend.annotation.RefactoringCandidate
import java.io.File
import java.nio.file.Path

@RefactoringCandidate
fun interface PathProtocol {

    fun processor(path: Path): File?

}