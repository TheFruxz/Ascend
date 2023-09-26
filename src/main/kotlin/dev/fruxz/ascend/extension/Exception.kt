package dev.fruxz.ascend.extension

import dev.fruxz.ascend.extension.container.emptyString
import dev.fruxz.ascend.extension.data.generateRandomTag
import dev.fruxz.ascend.extension.data.randomInt
import dev.fruxz.ascend.tool.exception.ExceptionCatch
import kotlin.random.Random

/**
 * Gets the exception [exception] and prints a beautiful stack trace & message.
 * @param exception the exception to print
 * @author Fruxz
 * @since 2023.1
 */
fun catchException(
	exception: Exception,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	random: Random = Random,
) {

	val tag = generateRandomTag(randomizer = random)
	val exceptionShort = exception.stackTrace.firstOrNull()?.className ?: "Unknown exception!"

	catch(exception, tag)

	println(" > $tag - $exceptionShort")
	exception.printStackTrace()
	println(" < $tag - $exceptionShort")

}

/**
 * Try return the value and returns the result inside a [Result] object.
 * @param T is the return type of the process
 * @param silent if set to false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] as a [Result]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> tryWithResult(
	silent: Boolean = true,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> T
): Result<T> = try {
	Result.success(process())
} catch (e: Exception) {
	when {
		!silent -> catchException(e)
		else -> catch.invoke(e, emptyString())
	}
	Result.failure(e)
}

/**
 * Try return the value and returns the result inside a [Result] object.
 * @param T is the return type of the process
 * @param silent if returns false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] as a [Result]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> tryWithResult(
	silent: () -> Boolean,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> T
): Result<T> = tryWithResult(silent = silent(), catch = catch, process = process)


/**
 * Try return the value returning of the [process] or returns the [other].
 * [other]-return is triggered by a thrown [Exception]
 * @param R is the return type of the process
 * @param T is the [other] type
 * @param silent if set to false, the exception will be printed
 * @param other the value to return if the process throws an [Exception]
 * @param process the process to execute, returning the normal value as [R]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] or the [other]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <R, T : R> tryOrElse(
	silent: Boolean = true,
	other: T,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> R
): R = tryWithResult(silent = silent, catch = catch, process = process).getOrElse { other }

/**
 * Try return the value returning of the [process] or returns the [other].
 * [other]-return is triggered by a thrown [Exception]
 * @param R is the return type of the process
 * @param T is the [other] type
 * @param silent if returns false, the exception will be printed
 * @param other the value to return if the process throws an [Exception]
 * @param process the process to execute, returning the normal value as [R]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] or the [other]
 * @author Fruxz
 * @since 2023.1
 */
inline fun <R, T : R> tryOrElse(
	silent: () -> Boolean,
	other: T,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> R
): R = tryOrElse(silent = silent(), other = other, catch = catch, process = process)


/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param T is the return type of the process
 * @param silent if set to false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> tryOrNull(
	silent: Boolean = true,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> T
): T? = tryWithResult(silent = silent, catch = catch, process = process).getOrNull()

/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param T is the return type of the process
 * @param silent if returns false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 2023.1
 */
inline fun <T> tryOrNull(
	silent: () -> Boolean,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> T
): T? = tryOrNull(silent = silent(), catch = catch, process = process)


/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @param silent if set to false, the exception will be printed
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @author Fruxz
 * @since 2023.1
 */
inline fun tryOrIgnore(
	silent: Boolean = true,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> Unit
): Unit = tryWithResult(silent = silent, catch = catch, process = process).dump()

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @param silent if returns false, the exception will be printed
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @author Fruxz
 * @since 2023.1
 */
inline fun tryOrIgnore(
	silent: () -> Boolean,
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> Unit
): Unit = tryOrIgnore(silent = silent(), catch = catch, process = process)


/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, the stack trace will be printed.
 * @param catch executes the code if an exception is thrown, even if it is successfully caught and avoided
 * @author Fruxz
 * @since 2023.1
 */
inline fun tryOrPrint(
	catch: ExceptionCatch<Exception> = ExceptionCatch.ignore(),
	process: () -> Unit
) = tryWithResult(catch = catch, process = process).exceptionOrNull()?.printStackTrace().dump()