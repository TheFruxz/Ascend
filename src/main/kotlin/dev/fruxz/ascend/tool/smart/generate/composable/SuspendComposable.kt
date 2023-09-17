package dev.fruxz.ascend.tool.smart.generate.composable

import dev.fruxz.ascend.tool.smart.generate.producible.ProducibleSuspended
import kotlin.coroutines.CoroutineContext

/**
 * This fun interface defines a code block, which can (often later) generate
 * an object of type [T] in a suspend context.
 * @author Fruxz
 * @since 2023.4
 */
fun interface SuspendComposable<T : Any> {

    suspend fun compose(context: CoroutineContext): T

    fun asProducible(context: CoroutineContext) = object : ProducibleSuspended<T> {
        override suspend fun produce(): T = compose(context = context)
    }

}