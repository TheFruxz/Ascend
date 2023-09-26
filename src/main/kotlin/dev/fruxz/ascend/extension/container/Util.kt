package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.extension.tryOrNull

/**
 * Creates a sublist of [this] List<[T]> using the [subList] function
 * but accept a [IntRange] as range instead.
 * @param values the [IntRange] to use as take parameter
 * @return the sublist of [this] List<[T]>
 * @author Fruxz
 * @since 2023.1
 */
fun <T> List<T>.subList(values: IntRange) = subList(values.first, values.last)

/**
 * Creates a sublist of [this] List<[T]> using the [subList] function
 * but accept a [IntRange] as range instead.
 * @param values the [IntRange] to use as take parameter
 * @return the sublist of [this] List<[T]> or an empty list if the range is invalid or empty
 * @see subList
 * @see tryOrNull
 * @see emptyList
 * @author Fruxz
 * @since 2023.2
 */
fun <T> List<T>.subListOrEmpty(values: IntRange) = tryOrNull {
    when {
        values.first > values.last -> emptyList()
        else -> subList(values.first, values.last)
    }
} ?: emptyList()

/**
 * [forEach] a [Collection] but instead of a 'it' lambda it uses a 'this' lambda,
 * which is the same as [forEach] a [Collection] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @author Fruxz
 * @since 2023.1
 */
inline fun <O, T : Iterable<O>> T.withForEach(action: O.() -> Unit) = forEach(action)

/**
 * [forEach] a [Array] but instead of a 'it' lambda it uses a 'this' lambda,
 * which is the same as [forEach] a [Array] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @author Fruxz
 * @since 2023.1
 */
inline fun <O> Array<O>.withForEach(action: O.() -> Unit) = forEach(action)

/**
 * Maps a [Collection] to a [List] using the [map] function, which
 * is the same as [map] a [Collection] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <I, O, T : Iterable<I>> T.withMap(action: I.() -> O) = map(action)

/**
 * Maps a [Array] to a [List] using the [map] function, which
 * is the same as [map] a [Array] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <I, O> Array<I>.withMap(action: I.() -> O) = map(action)

/**
 * Maps a [Map] using the [Map.map] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <K, V, O, T : Map<K, V>> T.withMap(action: Map.Entry<K, V>.() -> O) = map(action)

/**
 * Maps an [Iterable] to a [List] using the [mapNotNull] function, which
 * is the same as [mapNotNull] a [Collection] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List] without null values
 * @author Fruxz
 * @since 2023.1
 */
inline fun <I, O, T : Iterable<I>> T.withMapNotNull(action: I.() -> O?) = mapNotNull(action)

/**
 * Maps a [Array] to a [List] using the [mapNotNull] function, which
 * is the same as [mapNotNull] a [Array] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List] without null values
 * @author Fruxz
 * @since 2023.1
 */
inline fun <I, O> Array<I>.withMapNotNull(action: I.() -> O?) = mapNotNull(action)

/**
 * Maps a [Map] using the [Map.mapNotNull] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <K, V, O, T : Map<K, V>> T.withMapNotNull(action: Map.Entry<K, V>.() -> O?) = mapNotNull(action)