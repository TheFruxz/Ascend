package dev.fruxz.ascend.annotation

/**
 * Code annotated with this annotation is a candidate for refactoring
 * and may be changed or even removed in the future.
 * @author Fruxz
 * @since 2023.1
 */
internal annotation class RefactoringCandidate(
    val message: String = "",
    val since: String = ""
)
