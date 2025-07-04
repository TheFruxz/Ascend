@file:Suppress("UNCHECKED_CAST")
@file:OptIn(ExperimentalContracts::class)

package dev.fruxz.ascend.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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
inline fun <reified O> Any?.forceCastOrNull(): O? {
    contract { returnsNotNull() implies (this@forceCastOrNull is O) }

    return tryOrNull { this as? O }
}

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
inline fun <reified O> Any?.forceNullableCastOrNull(): O? {
    contract { returnsNotNull() implies (this@forceNullableCastOrNull is O) }

    return tryOrNull { this as? O? }
}

/**
 * Throws away the object by returning [Unit]
 * @return [Unit]
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("UnusedReceiverParameter")
fun <T> T.dump(): Unit = Unit