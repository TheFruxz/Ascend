package dev.fruxz.ascend.extension.math

import java.text.DecimalFormat

/**
 * Returns if the string can be parsed to a [Int].
 * @return true if the string can be parsed to a [Int], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isInt() = this?.toIntOrNull() != null

/**
 * Returns if the string can be parsed to a [Double].
 * @return true if the string can be parsed to a [Double], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isDouble() = this?.toDoubleOrNull() != null

/**
 * Returns if the string can be parsed to a [Float].
 * @return true if the string can be parsed to a [Float], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isFloat() = this?.toFloatOrNull() != null

/**
 * Returns if the string can be parsed to a [Long].
 * @return true if the string can be parsed to a [Long], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isLong() = this?.toLongOrNull() != null

/**
 * Returns if the string can be parsed to a [Byte].
 * @return true if the string can be parsed to a [Byte], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isByte() = this?.toByteOrNull() != null

/**
 * Returns if the string can be parsed to a [Boolean]. This function is *not* case sensitive.
 * @return true if the string can be parsed to a [toBooleanStrict], otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
fun String?.isBoolean() = this?.lowercase()?.toBooleanStrictOrNull() != null

/**
 * Formats the number to the specific [pattern] using a [DecimalFormat] with
 * its internal [format] function and the [this] number.
 * @param pattern the pattern to format the number with.
 * @return the formatted number as a string.
 * @author Fruxz
 * @since 2023.1
 */
infix fun Number.format(pattern: String): String = DecimalFormat(pattern).format(this)

/**
 * Formats the number to the '##.##' pattern using the [Number.format] function.
 * @return the formatted number as a [String].
 * @author Fruxz
 * @since 2023.1
 */
val Number.shorter: String
	get() = this.format("##.##")

/**
 * Formats the number to the '##.##' pattern using the [Number.format] function.
 * @return the formatted number as a double, using [String.toDouble] on the [String].
 * @author Fruxz
 * @since 2023.1
 */
val Number.shorterDouble: Double
	get() = shorter.toDouble()

/**
 * Formats the number to the '##.##' pattern using the [Number.format] function.
 * @return the formatted number as a float, using [String.toFloat] on the [String].
 * @author Fruxz
 * @since 2023.1
 */
val Number.shorterFloat: Float
	get() = shorter.toFloat()

/**
 * Adds the [intArray] to the [this] [IntArray] merging into a new array containing both.
 * @param intArray the array to add to the [this] array.
 * @return the merged array.
 * @author Fruxz
 * @since 2023.1
 */
operator fun IntArray.plus(intArray: IntArray) = asIterable().toList().toTypedArray() + intArray.toList()

/**
 * This computational value represents the range from the [Int.MIN_VALUE] to [Int.MAX_VALUE]
 * as a [IntRange]
 * @author Fruxz
 * @since 2023.1
 */
val Int.Companion.RANGE: IntRange by lazy { Int.MIN_VALUE..Int.MAX_VALUE }

/**
 * This computational value represents the range from the [Long.MIN_VALUE] to [Long.MAX_VALUE]
 * as a [LongRange]
 * @author Fruxz
 * @since 2023.1
 */
val Long.Companion.RANGE: LongRange by lazy { Long.MIN_VALUE..Long.MAX_VALUE }
