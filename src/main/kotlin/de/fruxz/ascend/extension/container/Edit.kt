package de.fruxz.ascend.extension.container

fun <I, O> Map<I, O>.edited(builder: MutableMap<I, O>.() -> Unit) = toMutableMap().apply(builder).toMap()

fun <O> List<O>.edited(builder: MutableList<O>.() -> Unit) = toMutableList().apply(builder).toList()

fun <O> Set<O>.edited(builder: MutableSet<O>.() -> Unit) = toMutableSet().apply(builder).toSet()

fun <O> Iterable<O>.edited(builder: MutableIterable<O>.() -> Unit) = toMutableList().apply(builder).toList()

inline fun <reified O> Array<O>.edited(builder: MutableList<O>.() -> Unit) = toMutableList().apply(builder).toTypedArray()