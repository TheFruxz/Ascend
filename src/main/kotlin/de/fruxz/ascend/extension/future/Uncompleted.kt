package de.fruxz.ascend.extension.future

import de.fruxz.ascend.extension.tryOrNull
import java.util.*

/**
 * Returns the value of the [Optional] or null if the optional
 * throws an exception.
 * @return the value of the [Optional] or null
 * @author Fruxz
 * @since 1.0
 */
fun <T> Optional<T>.getOrNull() = tryOrNull { get() }

/**
 * Returns the value of the [Optional] or null if the [Optional]
 * is null or if the [Optional] throws an exception.
 * @return the value of the [Optional] or null
 * @author Fruxz
 * @since 1.0
 */
@JvmName("nullableGetOrNullT")
fun <T> Optional<T>?.getOrNull() = tryOrNull { this?.get() }

/**
 * Returns the value of the [Optional] or the [default] parameter
 * if the [Optional] throws a [NoSuchElementException]
 * @param default the default value that gets returned if the [Optional] throws the [NoSuchElementException]
 * @return the value of the [Optional] or [default]
 * @author Fruxz
 * @since 1.0
 */
fun <T> Optional<T>.getOrDefault(default: T) = getOrNull() ?: default

/**
 * Returns the value of the [Optional] or the [default] parameter
 * if the [Optional] is null or if the [Optional] throws a [NoSuchElementException].
 * @param default the default value that gets returned if null or if the [Optional] throws the [NoSuchElementException]
 * @return the value of the [Optional] or [default]
 * @author Fruxz
 * @since 1.0
 */
@JvmName("nullableGetOrDefaultT")
fun <T> Optional<T>?.getOrDefault(default: T) = getOrNull() ?: default