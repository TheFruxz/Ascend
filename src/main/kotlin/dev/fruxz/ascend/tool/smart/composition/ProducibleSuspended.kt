package dev.fruxz.ascend.tool.smart.composition

/**
 * This interface marks every class/object, that can produce an object type [T]
 * of which the computation is suspended.
 * @author Fruxz
 * @since 2023.1
 */
interface ProducibleSuspended<T : Any> {

    /**
     * This function computes the end result of type [T] in a suspended context.
     * @return the result [T]
     */
    suspend fun produce(): T

}