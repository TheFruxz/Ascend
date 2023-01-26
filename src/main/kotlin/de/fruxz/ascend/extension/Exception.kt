package de.fruxz.ascend.extension

import de.fruxz.ascend.extension.data.randomInt
import kotlin.random.Random

/**
 * Gets the exception [exception] and prints a beautiful stack trace & message.
 * @param exception the exception to print
 * @author Fruxz
 * @since 1.0
 */
fun catchException(exception: Exception, random: Random = Random) {

	val tag = "#${randomInt(10_000..99_999, random)}"
	val exceptionShort = exception.stackTrace.firstOrNull()?.className ?: "Unknown exception!"

	println(" > $tag - $exceptionShort")
	exception.printStackTrace()
	println(" < $tag - $exceptionShort")

}

/**
 * Try return the value and returns the result inside a [Result] object.
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param silent if set to false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] as a [Result]
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, T> A.tryWithResult(silent: Boolean = true, process: A.() -> T): Result<T> {
	return try {
		Result.success(process())
	} catch (e: Exception) {
		if (!silent) catchException(e)
		Result.failure(e)
	}
}

/**
 * Try return the value and returns the result inside a [Result] object.
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param silent if returns false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] as a [Result]
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, T> A.tryWithResult(silent: () -> Boolean, process: A.() -> T): Result<T> =
	tryWithResult(silent = silent(), process = process)

/**
 * Try return the value returning of the [process] or returns the [other].
 * [other]-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param R is the return type of the process
 * @param T is the [other] type
 * @param silent if set to false, the exception will be printed
 * @param other the value to return if the process throws an [Exception]
 * @param process the process to execute, returning the normal value as [R]
 * @return the value returned by the [process] or the [other]
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, R, T : R> A.tryOrElse(silent: Boolean = true, other: T, process: A.() -> R): R {
	return tryWithResult(silent = silent, process = process).getOrElse { other }
}

/**
 * Try return the value returning of the [process] or returns the [other].
 * [other]-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param R is the return type of the process
 * @param T is the [other] type
 * @param silent if returns false, the exception will be printed
 * @param other the value to return if the process throws an [Exception]
 * @param process the process to execute, returning the normal value as [R]
 * @return the value returned by the [process] or the [other]
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, R, T : R> A.tryOrElse(silent: () -> Boolean, other: T, process: A.() -> R): R =
	tryOrElse(silent = silent(), other = other, process = process)

/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param silent if set to false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, T> A.tryOrNull(silent: Boolean = true, process: A.() -> T): T? =
	tryWithResult(silent = silent, process = process).getOrNull()

/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param silent if returns false, the exception will be printed
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 1.0
 */
inline fun <A, T> A.tryOrNull(silent: () -> Boolean, process: A.() -> T): T? =
	tryOrNull(silent = silent(), process = process)

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @param silent if set to false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryOrIgnore(silent: Boolean = true, process: A.() -> Unit) {
	tryWithResult(silent = silent, process = process)
}

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @param silent if returns false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryOrIgnore(silent: () -> Boolean, process: A.() -> Unit): Unit =
	tryOrIgnore(silent = silent(), process = process)

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, the stack trace will be printed.
 * @param silent if set to false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryOrPrint(process: A.() -> Unit) {
	tryWithResult(process = process).exceptionOrNull()?.printStackTrace()
}