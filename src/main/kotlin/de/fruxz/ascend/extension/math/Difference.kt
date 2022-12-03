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
infix fun Int.differenceTo(o: Int) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Long].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Long.differenceTo(o: Long) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Double].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Double.differenceTo(o: Double) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Float].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Float.differenceTo(o: Float) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Byte].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Byte.differenceTo(o: Byte) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Short].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
infix fun Short.differenceTo(o: Short) = abs(o - this)