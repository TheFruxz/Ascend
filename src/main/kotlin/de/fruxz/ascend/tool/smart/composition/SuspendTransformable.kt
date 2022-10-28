package de.fruxz.ascend.tool.smart.composition

fun interface SuspendTransformable<I, O> {

	suspend fun I.transform(): O

	suspend fun applyTo(other: I): O = other.transform()

	companion object {

		suspend fun <I, O> apply(input: I, transformable: SuspendTransformable<I, O>): O = with(transformable) { input.transform() }

	}

}