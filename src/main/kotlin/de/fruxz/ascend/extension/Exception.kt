package de.fruxz.ascend.extension

import kotlin.random.Random

/**
 * Gets the exception [exception] and prints a beautiful stack trace & message.
 * @param exception the exception to print
 * @author Fruxz
 * @since 1.0
 */
fun catchException(exception: Exception, random: Random = Random) {

	val exceptionIdentity = random.nextInt(10000, 99999)
	val exceptionTag = "#$exceptionIdentity"
	val exceptionShort = exception.stackTrace.firstOrNull()?.className ?: "Can't get short!"

	println(" > $exceptionTag - $exceptionShort")
	exception.printStackTrace()
	println(" < $exceptionTag - $exceptionShort")

}

/**
 * Try to execute the code specified inside the [process] function.
 * If an exception is thrown, the [catchException] function will be executed.
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param process the code to execute
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("Try to print seems like a good replacement!")
inline fun <A> A.tryToCatch(process: A.() -> Unit) {
	try {
		process(this)
	} catch (e: Exception) {
		catchException(e)
	}
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
inline fun <A, T> A.tryToResult(silent: Boolean = true, process: A.() -> T): Result<T> {
	return try {
		Result.success(process())
	} catch (e: Exception) {
		if (!silent) e.printStackTrace()
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
inline fun <A, T> A.tryToResult(silent: () -> Boolean, process: A.() -> T): Result<T> =
	tryToResult(silent = silent(), process = process)

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
	return tryToResult(silent, process).getOrElse { other }
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
	tryToResult(silent, process).getOrNull()

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
inline fun <A> A.tryToIgnore(silent: Boolean = true, process: A.() -> Unit) {
	tryToResult(silent, process)
}

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @param silent if returns false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryToIgnore(silent: () -> Boolean, process: A.() -> Unit): Unit =
	tryToIgnore(silent = silent(), process = process)

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, the stack trace will be printed.
 * @param silent if set to false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryToPrint(silent: Boolean = true, process: A.() -> Unit) {
	tryToResult(silent = silent, process = process).exceptionOrNull()?.printStackTrace()
}

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, the stack trace will be printed.
 * @param silent if returns false, the exception will be printed
 * @author Fruxz
 * @since 1.0
 */
inline fun <A> A.tryToPrint(silent: () -> Boolean, process: A.() -> Unit): Unit =
	tryToPrint(silent = silent(), process = process)