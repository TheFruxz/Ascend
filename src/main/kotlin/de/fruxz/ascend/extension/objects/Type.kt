package de.fruxz.ascend.extension.objects

inline fun <reified O> Any.takeIfInstance(): O? = when (this) {
	is O -> this
	else -> null
}