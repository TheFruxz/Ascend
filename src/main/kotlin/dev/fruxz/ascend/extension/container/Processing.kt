package dev.fruxz.ascend.extension.container

/**
 * This function allows you to sweep through a [MutableCollection] and process each element.
 *
 * This means, it iterates like this: `<collection> { <process> -> <remove> }`.
 * @author Fruxz
 * @since 2023.2
 */
fun <T> MutableCollection<T>.sweep(process: (T) -> Unit) {
    toList().forEach {
        process(it)
        remove(it)
    }
}