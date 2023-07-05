package dev.fruxz.ascend.extension.container

/**
 * Creates a new [MutableMap] by copying all the content from the current [MutableMap].
 *
 * @return A new [MutableMap] with the same key-value pairs as the current [MutableMap].
 *
 * @param T The type of the keys in the [MutableMap].
 * @param E The type of the values in the [MutableMap].
 * @param M The type of the [MutableMap] to copy.
 *
 * @receiver The current [MutableMap] to copy.
 *
 * @see MutableMap
 * @see MutableMap.toMutableMap
 */
fun <T, E, M : MutableMap<T, E>> M.copy() =
	this.toMutableMap()

/**
 * This function removes every element from this [MutableMap], which matches the [condition].
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T, E, M : MutableMap<T, E>> M.removeAll(condition: (key: T, value: E) -> Boolean) = copy().forEach { (key, value) ->
	if (condition(key, value)) {
		this.remove(key)
	}
}