package dev.fruxz.ascend.extension.container

/**
 * This function returns, if at least one element matches the [string] with [ignoreCase] in mind.
 * This utilizes the [String.equals] function in the [Array.any] process.
 * @author Fruxz
 * @since 2023.1
 */
fun Array<out String>.contains(string: String, ignoreCase: Boolean) = any { it.equals(string, ignoreCase) }