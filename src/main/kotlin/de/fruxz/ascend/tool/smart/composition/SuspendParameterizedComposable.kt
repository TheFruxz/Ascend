package de.fruxz.ascend.tool.smart.composition

fun interface SuspendParameterizedComposable<O, I> {
	suspend fun compose(parameter: I): O
}