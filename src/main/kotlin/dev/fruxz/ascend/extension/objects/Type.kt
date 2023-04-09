package dev.fruxz.ascend.extension.objects

import dev.fruxz.ascend.extension.forceCastOrNull

/**
 * This function returns [this] object, if it is of the given type [O].
 * If it is not, null will be returned.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified O> Any.takeIfInstance(): O? = when (this) {
	is O -> this
	else -> null
}

/**
 * This function returns [this] object, if it can be cast to the given type [O].
 * This can also happen, if [this] is of the type [O] or a subtype of [O].
 * This may be a suitable solution against the [takeIfInstance], if reified types are not supported.
 * @author Fruxz
 * @since 1.0
 */
fun <O> Any.takeIfCastableTo(): O? = this.forceCastOrNull()