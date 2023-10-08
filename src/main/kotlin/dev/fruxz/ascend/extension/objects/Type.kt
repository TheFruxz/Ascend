package dev.fruxz.ascend.extension.objects

/**
 * This function returns [this] object, if it is of the given type [O].
 * If it is not, null will be returned.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified O> Any.takeIfInstance(): O? = when (this) {
	is O -> this
	else -> null
}