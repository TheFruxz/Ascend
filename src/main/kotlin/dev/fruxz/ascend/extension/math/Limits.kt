package dev.fruxz.ascend.extension.math

/**
 * Limits [this] to the given [range]. If you want it to something like a [IntProgression],
 * use the [limitToIterable] function instead!
 * @param range the range to limit [this] to.
 * @return [this], or [ClosedRange.start] if [this] is smaller than [ClosedRange.start], or [ClosedRange.endInclusive] if [this] is bigger than [ClosedRange.endInclusive].
 * @see minTo
 * @See maxTo
 * @author Fruxz
 * @since 2023.1
 */
infix fun <C : Comparable<C>> C.limitTo(range: ClosedRange<C>): C {
    return when {
        this < range.start -> range.start
        this > range.endInclusive -> range.endInclusive
        else -> this
    }
}

/**
 * Limits [this] to the given [range]. If you want it to something like a [ClosedRange],
 * use the [limitTo] function instead!
 * The min of this [C] is the minimum value and the max of this [C] is the maximum value.
 * @param range the range to limit [this] to.
 * @see minTo
 * @See maxTo
 * @author Fruxz
 * @since 2023.1
 */
infix fun <C : Iterable<T>, T : Comparable<T>> T.limitToIterable(range: C): T {
    return when {
        this < range.min() -> range.min()
        this > range.max() -> range.max()
        else -> this
    }
}

/**
 * Limits [this] to the minimum of [minimum].
 * @param minimum the minimum to limit [this] to.
 * @return [this], or [minimum] if [this] is smaller than [minimum].
 * @author Fruxz
 * @since 2023.1
 */
infix fun <C : Comparable<C>> C.minTo(minimum: C) = this.coerceAtLeast(minimum)

/**
 * Limits [this] to the maximum of [maximum].
 * @param maximum the maximum to limit [this] to.
 * @return [this], or [maximum] if [this] is bigger than [maximum].
 * @author Fruxz
 * @since 2023.1
 */
infix fun <C : Comparable<C>> C.maxTo(maximum: C) = this.coerceAtMost(maximum)