@file:Suppress("UNCHECKED_CAST")

package dev.fruxz.ascend.extension

/**
 * Cast the given object to the given type and not highlight risky casts in the IDE.
 * @param O the type of the object to cast
 * @return the cast object
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UNCHECKED_CAST")
fun <O> Any?.forceCast() = this as O

/**
 * Cast the given object to the given type or null if fails and not highlight risky casts in the IDE.
 * @param O the type of the object to cast
 * @return the cast object
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UNCHECKED_CAST")
fun <O> Any?.forceCastOrNull() = tryOrNull { this as? O }

/**
 * Cast the given object to the given type and not highlight risky casts in the IDE.
 * @param O the type of the object to cast
 * @return the cast object
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UNCHECKED_CAST")
fun <O> Any?.forceNullableCast() = this as O?

/**
 * Cast the given object to the given type or null if fails and not highlight risky casts in the IDE.
 * @param O the type of the object to cast
 * @return the cast object
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UNCHECKED_CAST")
fun <O> Any?.forceNullableCastOrNull() = tryOrNull { this as? O? }

/**
 * Throws away the object by returning [Unit]
 * @return [Unit]
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UnusedReceiverParameter")
fun <T> T.dump(): Unit = Unit