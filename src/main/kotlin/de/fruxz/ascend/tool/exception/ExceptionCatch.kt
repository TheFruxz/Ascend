package de.fruxz.ascend.tool.exception

import de.fruxz.ascend.extension.data.buildRandomTag

/**
 * This function-interface helps to add custom-crafted exception catches through api's.
 * This api is to keep try-catches away from other kotlin api, but this comes at the cost
 * of a lack of some try-catch-features.
 * @author Fruxz
 * @since 1.0
 */
fun interface ExceptionCatch<E : Throwable> {

    /**
     * This function handles the exception [throwable].
     * If the exception got through a helper, a [tag] will
     * be provided in the form of '#xxxxxx' generated with
     * [buildRandomTag].
     */
    fun handleException(throwable: E, tag: String)

    /**
     * Invokes the [handleException] function
     */
    operator fun invoke(throwable: E, tag: String) = handleException(throwable, tag)

    companion object {

        fun <E : Throwable> ignore() = ExceptionCatch<E> { _, _ -> return@ExceptionCatch }

        fun <E : Throwable> log() = ExceptionCatch<E> { error, tag -> println("Error: ($tag) '${error.message}'") }

    }

}