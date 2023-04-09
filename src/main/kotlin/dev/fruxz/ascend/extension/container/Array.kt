package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.annotation.LanguageFeature

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Int.Companion.get(vararg int: Int) = arrayOf(*int.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Long.Companion.get(vararg long: Long) = arrayOf(*long.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Float.Companion.get(vararg float: Float) = arrayOf(*float.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Double.Companion.get(vararg double: Double) = arrayOf(*double.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Boolean.Companion.get(vararg boolean: Boolean) = arrayOf(*boolean.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Char.Companion.get(vararg char: Char) = arrayOf(*char.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Byte.Companion.get(vararg byte: Byte) = arrayOf(*byte.toTypedArray())

@dev.fruxz.ascend.annotation.LanguageFeature
operator fun Short.Companion.get(vararg short: Short) = arrayOf(*short.toTypedArray())