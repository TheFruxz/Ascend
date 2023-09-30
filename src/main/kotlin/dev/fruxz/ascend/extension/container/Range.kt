package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.annotation.LanguageFeature
import kotlin.math.absoluteValue
import kotlin.time.measureTime

/**
 * Removes every element from this collection that is contained in the [ints] collection.
 * @param ints the collection of ints to remove
 * @return the elements of the range minus the elements of the [ints] collection
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
fun IntRange.skip(vararg ints: Int) =
	modified { removeAll(ints.toSet()) }

/**
 * Removes the [int] from this range.
 * @param int the int to remove
 * @return the elements of the range minus the [int]
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
infix fun IntRange.skip(int: Int) =
	skip(ints = intArrayOf(int))

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 2023.1
 */
val Iterable<Int>.span: Int
	get() = sorted().let { it.last() - it.first() }.absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 2023.1
 */
val Iterable<Long>.span: Long
	get() = sorted().let { it.last() - it.first() }.absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 2023.1
 */
val Iterable<Float>.span: Float
	get() = sorted().let { it.last() - it.first() }.absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 2023.1
 */
val Iterable<Double>.span: Double
	get() = sorted().let { it.last() - it.first() }.absoluteValue