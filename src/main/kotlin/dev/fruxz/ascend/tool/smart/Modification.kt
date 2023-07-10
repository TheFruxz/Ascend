package dev.fruxz.ascend.tool.smart

/**
 * Functional interface for performing modifications on a value of type T.
 * Can be used as a replacement for [kotlin.jvm.functions.Function1] / simple lambda.
 * @param T the type of value to be modified
 * @author Fruxz
 * @see 2023.3
 */
fun interface Modification<T> {
    /**
     * The process, which should modify the [value] as needed.
     * @author Fruxz
     * @see 2023.3
     */
    fun modify(value: T)
}

/**
 * Creates a [Modification] from a simple lambda, provided by [modification], representing [Modification.modify].
 * A [Modification] itself represents a process(or), which edits and works with an object.
 * @author Fruxz
 * @see 1.0
 */
fun <T> modification(modification: T.() -> Unit) = Modification<T> { modification(it) }