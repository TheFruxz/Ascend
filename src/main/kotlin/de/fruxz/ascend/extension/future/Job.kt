package de.fruxz.ascend.extension.future

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

/**
 * This function is a wrapper for [Job.join]!
 */
suspend fun Job.await() = this.join()

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
fun <I, O> Deferred<I>.mapOnCompletion(process: (I) -> O) = CompletableDeferred<O>().also {

	@OptIn(ExperimentalCoroutinesApi::class)
	invokeOnCompletion { exception ->
		when (exception) {
			null -> it.complete(process(this.getCompleted()))
			else -> it.completeExceptionally(exception)
		}
	}
}