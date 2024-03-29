package dev.fruxz.ascend.tool.exception

import java.util.logging.Logger

/**
 * This function-interface helps to add custom-crafted exception catches through api's.
 * This api is to keep try-catches away from other kotlin api, but this comes at the cost
 * of a lack of some try-catch-features.
 * @author Fruxz
 * @since 2023.1
 */
fun interface ExceptionCatch<E : Throwable> {

    /**
     * This function handles the exception [throwable].
     * If the exception got through a helper, a [tag] will
     * be provided.
     */
    fun handleException(throwable: E, tag: String)

    /**
     * Invokes the [handleException] function
     */
    operator fun invoke(throwable: E, tag: String) = handleException(throwable, tag)

    companion object {

        /**
         * A utility method that ignores the occurrence of a specific type of throwable.
         * It creates an [ExceptionCatch] instance for the given throwable type [E] with empty catch block.
         *
         * @return An [ExceptionCatch] instance that ignores the occurrence of throwable [E].
         */
        fun <E : Throwable> ignore() = ExceptionCatch<E> { _, _ -> return@ExceptionCatch }

        /**
         * Logs the provided error message with the given tag.
         *
         * @param E the type of throwable to catch.
         * @return an instance of [ExceptionCatch] with the specified error logging behavior.
         */
        fun <E : Throwable> log() = ExceptionCatch<E> { error, tag -> println("Exception: ($tag) '${error.message}'") }

        fun <E : Throwable> log(logger: Logger) = ExceptionCatch<E> { error, tag -> logger.severe("Exception: ($tag) '${error.message}'") }

    }

}