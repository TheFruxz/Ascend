package dev.fruxz.ascend.tool.smart.generate.composable

import dev.fruxz.ascend.tool.smart.generate.producible.Producible

/**
 * This fun interface defines a code block, which can (often later) generate
 * an object of type [T].
 * @author Fruxz
 * @since 2023.4
 */
fun interface Composable<T : Any> : Producible<T> {

    fun compose(): T

    override fun produce(): T = compose()

}