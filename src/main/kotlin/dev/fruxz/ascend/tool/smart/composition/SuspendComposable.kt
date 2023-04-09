package dev.fruxz.ascend.tool.smart.composition

import kotlinx.coroutines.CoroutineScope

fun interface SuspendComposable<O> {
	suspend fun compose(scope: CoroutineScope): O
}