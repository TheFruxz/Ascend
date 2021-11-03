package de.jet.minecraft.tool.data

import java.nio.file.Path

interface JetFile {

	val file: Path

	fun load()

	fun save()

	fun contains(path: String): Boolean

	operator fun <T : Any?> set(path: String, value: T)

	operator fun <T> get(path: String): T?

}