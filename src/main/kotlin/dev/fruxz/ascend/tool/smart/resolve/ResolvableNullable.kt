package dev.fruxz.ascend.tool.smart.resolve

import kotlin.jvm.Throws

/**
 * Represents a resolvable value that can be nullable.
 *
 * @param T the type of the value to resolve.
 */
interface ResolvableNullable<T> : Resolvable<T & Any> {

    /**
     * Resolves the value, returning null if it cannot be resolved.
     */
    fun resolveOrNull(): T?

    /**
     * Resolves the value, throwing an exception if it cannot be resolved.
     *
     * @throws NoSuchElementException if the resolved value is null.
     */
    @Throws(NoSuchElementException::class)
    override fun resolve(): T & Any =
        resolveOrNull() ?: throw NoSuchElementException("Cannot resolve null value for ${this::class.simpleName}.")

}