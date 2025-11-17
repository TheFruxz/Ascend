@file:OptIn(InternalUseOnly::class)

package dev.fruxz.ascend.extension.property

import dev.fruxz.ascend.annotation.InternalUseOnly
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.KProperty2
import kotlin.reflect.jvm.isAccessible

@InternalUseOnly
fun <T : KProperty<*>, O> T.propertyResolver(getter: (T) -> O): O {
    this.isAccessible = true
    return getter(this)
}

/**
 * Resolves the delegate of a property, returning null if the property is not delegated or if the delegate is not of the expected type.
 * Useful for safely accessing delegated properties without risking exceptions. Like accessing the last update time of a [CachedProperty].
 * @author Fruxz
 * @since 2025.11
 */
inline fun <reified T> KProperty0<*>.delegateOrNull(): T? =
    propertyResolver { it.getDelegate() } as? T

/**
 * Resolves the delegate of a property, returning null if the property is not delegated or if the delegate is not of the expected type.
 * Useful for safely accessing delegated properties without risking exceptions. Like accessing the last update time of a [CachedProperty].
 * @author Fruxz
 * @since 2025.11
 */
inline fun <reified T, A> KProperty1<A, *>.delegateOrNull(receiver: A): T? =
    propertyResolver { it.getDelegate(receiver) } as? T

/**
 * Resolves the delegate of a property, returning null if the property is not delegated or if the delegate is not of the expected type.
 * Useful for safely accessing delegated properties without risking exceptions. Like accessing the last update time of a [CachedProperty].
 * @author Fruxz
 * @since 2025.11
 */
inline fun <reified T, A, B> KProperty2<A, B, *>.delegateOrNull(receiver1: A, receiver2: B): T? =
    propertyResolver { it.getDelegate(receiver1, receiver2) } as? T

/**
 * Resolves the delegate of a property, throwing an [UnresolvableDelegateException] if the property is not delegated or if the delegate is not of the expected type.
 * Useful for example, for accessing a last update time of a [CachedProperty]
 * @throws UnresolvableDelegateException if the property is not delegated or if the delegate is not of the expected type.
 * @author Fruxz
 * @since 2025.11
 */
@Throws(UnresolvableDelegateException::class)
inline fun <reified T> KProperty0<*>.delegate(): T & Any =
    delegateOrNull() ?: throw UnresolvableDelegateException("Property ${this.name} is not delegated or is not of type ${T::class.simpleName}")

/**
 * Resolves the delegate of a property, throwing an [UnresolvableDelegateException] if the property is not delegated or if the delegate is not of the expected type.
 * Useful for example, for accessing a last update time of a [CachedProperty]
 * @throws UnresolvableDelegateException if the property is not delegated or if the delegate is not of the expected type.
 * @author Fruxz
 * @since 2025.11
 */
@Throws(UnresolvableDelegateException::class)
inline fun <reified T, A> KProperty1<A, *>.delegate(receiver: A): T & Any =
    delegateOrNull(receiver) ?: throw UnresolvableDelegateException("Property ${this.name} is not delegated or is not of type ${T::class.simpleName}")

/**
 * Resolves the delegate of a property, throwing an [UnresolvableDelegateException] if the property is not delegated or if the delegate is not of the expected type.
 * Useful for example, for accessing a last update time of a [CachedProperty]
 * @throws UnresolvableDelegateException if the property is not delegated or if the delegate is not of the expected type.
 * @author Fruxz
 * @since 2025.11
 */
@Throws(UnresolvableDelegateException::class)
inline fun <reified T, A, B> KProperty2<A, B, *>.delegate(receiver1: A, receiver2: B): T & Any =
    delegateOrNull(receiver1, receiver2) ?: throw UnresolvableDelegateException("Property ${this.name} is not delegated or is not of type ${T::class.simpleName}")