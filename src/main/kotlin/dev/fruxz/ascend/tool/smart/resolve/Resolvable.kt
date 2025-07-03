package dev.fruxz.ascend.tool.smart.resolve

/**
 * Represents a resolvable value of type [T].
 *
 * This interface is used to define a contract for classes that can resolve
 * and provide a value of type [T]. The `resolve` method should be implemented
 * to return the resolved value.
 *
 * Return is always non-nullable, so the implementation must ensure that, for nullable results, use [ResolvableNullable] instead!
 *
 * @param T The type of the value to be resolved.
 */
interface Resolvable<T : Any> {

    fun resolve(): T

}