package de.fruxz.ascend.tool.smart.composition

fun interface Transformable<I, O> {

	fun I.transform(): O

	fun applyTo(other: I): O = other.transform()

	companion object {

		fun <I, O> apply(input: I, transformable: Transformable<I, O>): O = with(transformable) { input.transform() }

	}

}