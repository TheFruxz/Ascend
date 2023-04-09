package de.fruxz.ascend.extension.future

import de.fruxz.ascend.extension.switchResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

/**
 * This function is a wrapper for [Job.join]!
 */
suspend fun Job.await() = this.join()

suspend fun List<Job>.awaitAll() = this.map { it.await() }

suspend fun List<Job>.awaitAll(printOut: Boolean) = when (printOut) {
	false -> this.awaitAll()
	else -> {
		var currentState = 0

		map { job ->
			job.invokeOnCompletion { error ->
				currentState++
				println("Job [$size/$currentState] ${(error == null).switchResult("finished", "failed")}!")
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
 * @since 1.0
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
 */
fun <T> deferred(job: Job? = null, builder: (CompletableDeferred<T>) -> Unit = { }) = CompletableDeferred<T>(job).apply(builder)

suspend fun <T> List<Deferred<T>>.awaitAll() = this.map { it.await() }