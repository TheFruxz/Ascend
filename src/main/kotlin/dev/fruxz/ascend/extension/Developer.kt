package dev.fruxz.ascend.extension

import dev.fruxz.ascend.annotation.LanguageFeature

/**
 * Executes a check, if all [objects] are passing the [check] check.
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
fun <T> all(vararg objects: T, check: (T) -> Boolean) = objects.all(check)

/**
 * Executes a check, if none of the [objects] are passing the [check] check.
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
fun <T> none(vararg objects: T, check: (T) -> Boolean) = objects.none(check)

/**
 * Executes a check, if at least one of the [objects] is passing the [check] check.
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
fun <T> any(vararg objects: T, check: (T) -> Boolean) = objects.any(check)

/**
 * Returning the [this]<[T]> modified with the [modification] if [modifyIf] is true,
 * otherwise returning the [this]<[T]> directly.
 * @param modifyIf if true modification is returned, else original
 * @param modification the modification to apply
 * @return the [this]<[T]> modified with the [modification] if [modifyIf] is true, otherwise returning the [this]<[T]> directly.
 * @author Fruxz
 * @since 2023.1
 */
fun <T> T.modifiedIf(modifyIf: Boolean, modification: (T) -> T) = when {
	modifyIf -> modification(this)
	else -> this
}

/**
 * Applying the [modification] to [this] if [modifyIf] is true,
 * otherwise to nothing to [this].
 * @param modifyIf if true modification is applied, else keep original
 * @param modification the modification to apply
 * @author Fruxz
 * @since 2023.1
 */
fun <T> T.modifyIf(modifyIf: Boolean, modification: T.() -> Unit) = when {
	modifyIf -> apply(modification)
	else -> this
}

/**
 * This function returns the modified version of [this] via [modification], or
 * if an exception gets thrown by the [modification] process, the original [this] is returned.
 * @author Fruxz
 * @since 2023.1
 */
fun <T> T.modifiedIfSuccess(modification: (T) -> T): T = tryOrNull { modification.invoke(this) } ?: this

/**
 * Returns true if this [Any]? is null, otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
val Any?.isNull: Boolean // TODO: use kotlin contracts
	get() = this == null

/**
 * Returns true if this [Any]? is not null, otherwise false.
 * @author Fruxz
 * @since 2023.1
 */
@LanguageFeature
val Any?.isNotNull: Boolean // TODO: use kotlin contracts
	get() = !isNull

/**
 * Only an empty body function.
 * @author Fruxz
 * @since 2023.1
 */
fun empty() { }

/**
 * Returns the [this]<[T]> modified with the [process] if [isNull] is true,
 * else return the unmodified state
 * @param process is the optional modification process
 * @author Fruxz
 * @since 2023.1
 */
fun <T> T?.applyIfNull(process: (T?) -> Unit) = modifyIf(isNull, process)

/**
 * Returns the [this]<[T]> modified with the [process] if [isNotNull] is true,
 * else return the unmodified state
 * @param process is the optional modification process
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> T?.applyIfNotNull(crossinline process: (T & Any) -> Unit) = modifyIf(isNotNull) {
	process(this!!)
}

/**
 * Executes the [process], if the [T] (nullable) is null ([isNull])
 * @param process is the optional execution process
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> T?.ifNull(process: () -> Unit) = if (isNull) process() else empty()

/**
 * Executes the [process], if the [T] (nullable) is notNull ([isNotNull])
 * @param process is the optional execution process
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> T?.ifNotNull(process: () -> Unit) = if (isNotNull) process() else empty()

/**
 * Returns [Pair.first] if [T]? [isNotNull], else use [Pair.second]
 * @author Fruxz
 * @since 2023.1
 */
fun <T, D> Pair<T?, D>.asDefaultNullDodge() = first.isNull.switch(first, second)
