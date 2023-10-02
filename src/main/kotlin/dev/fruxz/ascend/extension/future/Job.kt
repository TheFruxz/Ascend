package dev.fruxz.ascend.extension.future

import dev.fruxz.ascend.extension.switch
import kotlinx.coroutines.*

/**
 * Suspends the current coroutine until the completion of the [Job].
 * This function is a convenient wrapper for [Job.join].
 *
 * @receiver The [Job] instance to await for.
 * @throws CancellationException If the current coroutine was cancelled.
 * @author Fruxz
 * @since 2023.1
 */
suspend fun Job.await() = this.join()

/**
 * Suspends execution until all the [Job] in the given list are completed.
 *
 * This method takes a list of [Job] and suspends the execution of the current coroutine
 * until all the jobs are completed. It uses the `await` method on each job to wait for
 * their completion. The method returns a new list containing the results of all the jobs
 * in the same order as the input list.
 *
 * @return A new list containing the results of all the jobs in the same order as the input list.
 * @throws CancellationException If any of the jobs in the list is canceled.
 * @see Job.await
 * @author Fruxz
 * @since 2023.1
 */
suspend fun List<Job>.awaitAll() = this.map { it.await() }

/**
 * Awaits the completion of all jobs in the list.
 *
 * @param printOut Whether to print the progress of each job. If set to `true`, the method will print the current
 * state of each job as it completes. If set to `false`, no progress will be printed.
 * @return A [Job] that represents the completion of all the jobs in the list.
 * @author Fruxz
 * @since 2023.1
 */
suspend fun List<Job>.awaitAll(printOut: Boolean) = when (printOut) {
	false -> this.awaitAll()
	else -> {
		var currentState = 0

		map { job ->
			job.invokeOnCompletion { error ->
				currentState++
				println("Job [$size/$currentState] ${(error == null).switch("finished", "failed")}!")
			}
		}

	}
}

/**
 * This function allows to edit the result of [this] deferred on completion,
 * via the [process] function. Then a new [CompletableDeferred] object will
 * be created, to return the new result.
 * This helps to easily edit/map results, without having to directly [Deferred.await]
 * on them.
 * @param process is the process, to transform/map the result, like [Deferred.invokeOnCompletion], it is executed synchronously.
 * @author Fruxz
 * @since 2023.1
 */
fun <I, O> Deferred<I>.letOnCompletion(process: (I) -> O) = deferred { deferred ->

	@OptIn(ExperimentalCoroutinesApi::class)
	invokeOnCompletion { exception ->
		when (exception) {
			null -> deferred.complete(process(this.getCompleted()))
			else -> deferred.completeExceptionally(exception)
		}
	}

}

/**
 * This function creates a new [CompletableDeferred] of the type [T], optionally it is the child of [job].
 * Then it applies the [builder] function on it, so that you can easily edit it.
 * @author Fruxz
 * @since 2023.1
 */
fun <T> deferred(job: Job? = null, builder: (CompletableDeferred<T>) -> Unit = { }) = CompletableDeferred<T>(job).apply(builder)

/**
 * Suspends the current coroutine until all the deferred tasks in the given list have completed,
 * and returns a list of the results.
 *
 * @param T the type of the deferred tasks' results
 * @return a list, of the results, of the completed deferred tasks
 * @author Fruxz
 * @since 2023.1
 */
@JvmName("awaitAllDeferred")
suspend fun <T> List<Deferred<T>>.awaitAll() = this.map { it.await() }