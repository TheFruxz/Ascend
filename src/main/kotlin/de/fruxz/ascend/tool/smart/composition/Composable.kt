package de.fruxz.ascend.tool.smart.composition

fun interface Composable<O> {
	fun compose(): O
}