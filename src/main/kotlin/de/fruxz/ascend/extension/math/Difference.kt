package de.fruxz.ascend.extension.math

import kotlin.math.abs


/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Int].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Int.difference(o: Int) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Long].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Long.difference(o: Long) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Double].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Double.difference(o: Double) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Float].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Float.difference(o: Float) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Byte].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Byte.difference(o: Byte) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Short].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Short.difference(o: Short) = abs(o - this)