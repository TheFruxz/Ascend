package dev.fruxz.ascend.tool.smart.generate.composable

import dev.fruxz.ascend.tool.smart.generate.producible.ProducibleSuspended
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.CoroutineContext

/**
 * This fun interface defines a code block, which can (often later) generate
 * an object of type [T] in a suspend context.
 * @author Fruxz
 * @since 2023.4
 */
fun interface SuspendComposable<T : Any> : ProducibleSuspended<T> {

    suspend fun compose(context: CoroutineContext): T
    
    override suspend fun produce(): T = compose(currentCoroutineContext())

}