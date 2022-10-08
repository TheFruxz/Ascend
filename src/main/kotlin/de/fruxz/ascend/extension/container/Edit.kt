package de.fruxz.ascend.extension.container

/**
 * This function takes the input [Map], converts it into a [MutableMap] using
 * the [toMutableMap] function, applies the [builder] function to it and returns
 * the result as a [Map].
 * @author Fruxz
 * @since 1.0
 */
fun <I, O> Map<I, O>.edited(builder: MutableMap<I, O>.() -> Unit) = toMutableMap().apply(builder).toMap()

/**
 * This function takes the input [List], converts it into a [MutableList] using
 * the [toMutableList] function, applies the [builder] function to it and returns
 * the result as a [List].
 * @author Fruxz
 * @since 1.0
 */
fun <O> List<O>.edited(builder: MutableList<O>.() -> Unit) = toMutableList().apply(builder).toList()

/**
 * This function takes the input [Set], converts it into a [MutableSet] using
 * the [toMutableSet] function, applies the [builder] function to it and returns
 * the result as a [Set].
 * @author Fruxz
 * @since 1.0
 */
fun <O> Set<O>.edited(builder: MutableSet<O>.() -> Unit) = toMutableSet().apply(builder).toSet()

/**
 * This function takes the input [Iterable], converts it into a [MutableList] using
 * the [toMutableList] function, applies the [builder] function to it and returns
 * the result as a [List].
 * @author Fruxz
 * @since 1.0
 */
fun <O> Iterable<O>.edited(builder: MutableIterable<O>.() -> Unit) = toMutableList().apply(builder).toList()

/**
 * This function takes the input [Array], converts it into a [MutableList] using
 * the [toMutableList] function, applies the [builder] function to it and returns
 * the result as a [List].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified O> Array<O>.edited(builder: MutableList<O>.() -> Unit) = toMutableList().apply(builder).toTypedArray()