package dev.fruxz.ascend.tool.smart.composition

fun interface ParameterizedComposable<O, I> {
	fun compose(parameter: I): O
}