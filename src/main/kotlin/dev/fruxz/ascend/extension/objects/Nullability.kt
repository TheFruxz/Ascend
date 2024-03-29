package dev.fruxz.ascend.extension.objects

import dev.fruxz.ascend.extension.tryOrNull
import org.jetbrains.annotations.NotNull

/**
 * Converts a nullable object/value to a non-null object/value, if it is not null.
 * If it is null it throws the [throwable].
 *
 * internally a [tryOrNull] function is called, that forces
 * this as [NotNull] and if that fails, the null get returned
 * as `throw [throwable]`
 *
 * @param throwable the exception, thrown if the object is null
 * @return the non-null object
 * @throws NoSuchElementException if null
 * @author Fruxz
 * @since 2023.1
 */
fun <T : Any> T?.trustOrThrow(throwable: Throwable): T = tryOrNull { this!! } ?: throw throwable

/**
 * Forces an object ([this]) to be a non-null ([NotNull]) object.
 * This works as an alternative to the kotlin '!!' language feature,
 * to be called easily during indirect processes and avoid code
 * highlighting.
 *
 * If [this] is indeed null, this function throws a detailed direct
 * [NoSuchElementException].
 * If everything is fine, and it can be forced to a [NotNull], than
 *
 * Internally, the [trustOrThrow] function is called, with the
 * [NoSuchElementException] as the parameter, so internally
 * a [tryOrNull] function is called, that forces this as [NotNull]
 * and if that fails, the null get returned as `throw throwable`
 * @throws NoSuchElementException if null
 * @return the non-null object
 * @author Fruxz
 * @since 2023.1
 */
@NotNull
@Throws(NoSuchElementException::class)
inline fun <reified T : Any> T?.trust() =
	trustOrThrow(NoSuchElementException("Element of type [${T::class.simpleName}] was null. A null element of this type cannot be trusted. Please refer to Ascend:Nullability.kt:trust() for more information."))

/**
 * This function calls the [trust] function and can result
 * to a [NoSuchElementException]. Please see [trust]!
 * This function is called `trustByParameter` on the JVM
 * backend side.
 * @param o the object to be trusted
 * @return the non-null object
 * @see trust
 * @throws NoSuchElementException if null
 * @author Fruxz
 * @since 2023.1
 */
@JvmName("trustByParameter")
inline fun <reified T : Any> trust(o: T?) = o.trust()

/**
 * This function performs the [trust] function, on every
 * element of the [Iterable]<[T]?>, by using the [map]
 * function from the [Iterable]<out T> interface.
 *
 * ***The filterNotNull function is mostly the best choice***
 * @return a [List]<[T]> of the trusted objects
 * @throws NoSuchElementException if one element is null
 * @author Fruxz
 * @since 2023.1
 * @see trust
 * @see Iterable
 * @see List
 * @see map
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Any> Iterable<T?>.trustAll(): List<T> = map(::trust)
