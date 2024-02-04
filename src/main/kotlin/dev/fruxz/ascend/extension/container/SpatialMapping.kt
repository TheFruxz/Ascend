package dev.fruxz.ascend.extension.container

/**
 * This function is like the [Iterable.map] function, but it always also provides the previous and next element.
 * If there is no previous or next element, it will be null.
 * It has to be noted that the previous element is the non-modified original element, so if the current
 * is mapped, the next ones previous will not be the mapped current element.
 * @param block The block to map the elements.
 * @return A list of the mapped elements.
 * @author Fruxz
 * @since 2024.1
 */
fun <T, O> Iterable<T>.spatialMap(block: (previous: T?, current: T, next: T?) -> O): List<O> {
    val list = this.toList()
    return list.mapIndexed { index, t ->
        val previous = list.getOrNull(index - 1)
        val next = list.getOrNull(index + 1)
        block(previous, t, next)
    }
}

/**
 * This function is like the [Iterable.mapIndexed] function, but it always also provides the previous and next element.
 * If there is no previous or next element, it will be null.
 * It has to be noted that the previous element is the non-modified original element, so if the current
 * is mapped, the next ones previous will not be the mapped current element.
 * @param block The block to map the elements.
 * @return A list of the mapped elements.
 * @author Fruxz
 * @since 2024.1
 */
fun <T, O> Iterable<T>.spatialMapIndexed(block: (index: Int, previous: T?, current: T, next: T?) -> O): List<O> {
    val list = this.toList()
    return list.mapIndexed { index, t ->
        val previous = list.getOrNull(index - 1)
        val next = list.getOrNull(index + 1)
        block(index, previous, t, next)
    }
}

/**
 * This function is like the [Iterable.mapNotNull] function, but it always also provides the previous and next element.
 * If there is no previous or next element, it will be null.
 * It has to be noted that the previous element is the non-modified original element, so if the current
 * is mapped, the next ones previous will not be the mapped current element.
 * @param block The block to map the elements.
 * @return A list of the mapped elements, which are not null.
 * @see spatialMap
 * @since 2024.1
 * @author Fruxz
 */
fun <T> Iterable<T>.spatialMapNotNull(block: (previous: T?, current: T, next: T?) -> T?) = spatialMap(block).filterNotNull()

/**
 * This function is like the [Iterable.mapNotNull] function, but it always also provides the previous and next element and the index.
 * If there is no previous or next element, it will be null.
 * It has to be noted that the previous element is the non-modified original element, so if the current
 * is mapped, the next ones previous will not be the mapped current element.
 * @param block The block to map the elements.
 * @return A list of the mapped elements, which are not null.
 * @see spatialMapIndexed
 * @since 2024.1
 * @author Fruxz
 */
fun <T> Iterable<T>.spatialMapNotNullIndexed(block: (index: Int, previous: T?, current: T, next: T?) -> T?) = spatialMapIndexed(block).filterNotNull()