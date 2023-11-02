package dev.fruxz.ascend.extension.classType

import dev.fruxz.ascend.extension.math.limitTo
import kotlin.enums.enumEntries

/**
 * Returns the next enum in the ordinal order. If the current enum is the last one, the first
 * one is returned if [overflow] is true, otherwise it returns itself.
 * @param overflow if true, the first enum is returned if the current enum is the last one.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.next(overflow: Boolean = true): T =
	@OptIn(ExperimentalStdlibApi::class) enumEntries<T>()[
		when {
			overflow -> (ordinal + 1) % enumEntries<T>().size
			else -> (ordinal + 1).limitTo(0..enumEntries<T>().lastIndex)
		}]

/**
 * Returns the previous enum in the ordinal order. If the current enum is the first one, the last
 * one is returned if [overflow] is true, otherwise it returns itself.
 * @param overflow if true, the last enum is returned if the current enum is the first one.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.previous(overflow: Boolean = true): T =
	@OptIn(ExperimentalStdlibApi::class) when {
		overflow -> enumEntries<T>()[(ordinal - 1).takeIf { it < 0 } ?: enumEntries<T>().lastIndex]
		else -> enumEntries<T>()[(ordinal - 1).limitTo(0..enumEntries<T>().lastIndex)]
	}

/**
 * Returns the next enum in the ordinal order, or null, if this is already the last enum.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrNull(): T? = when (ordinal) {
	@OptIn(ExperimentalStdlibApi::class) enumEntries<T>().lastIndex -> null
	else -> next()
}

/**
 * Returns the previous enum in the ordinal order, or null, if this is already the first enum.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrNull(): T? = if (ordinal == 0) null else previous()

/**
 * Returns the next enum in the ordinal order, or if this enum is already the last enum, returns the first enum
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrFirst(): T = nextOrNull() ?: @OptIn(ExperimentalStdlibApi::class) enumEntries<T>().first()

/**
 * Returns the previous enum in the ordinal order, or if this enum is already the first enum, returns the last enum
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrLast(): T = previousOrNull() ?: @OptIn(ExperimentalStdlibApi::class) enumEntries<T>().last()