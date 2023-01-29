package de.fruxz.ascend.extension.container

import de.fruxz.ascend.annotation.LanguageFeature

@LanguageFeature
operator fun Int.Companion.get(vararg int: Int) = arrayOf(*int.toTypedArray())

@LanguageFeature
operator fun Long.Companion.get(vararg long: Long) = arrayOf(*long.toTypedArray())

@LanguageFeature
operator fun Float.Companion.get(vararg float: Float) = arrayOf(*float.toTypedArray())

@LanguageFeature
operator fun Double.Companion.get(vararg double: Double) = arrayOf(*double.toTypedArray())

@LanguageFeature
operator fun Boolean.Companion.get(vararg boolean: Boolean) = arrayOf(*boolean.toTypedArray())

@LanguageFeature
operator fun Char.Companion.get(vararg char: Char) = arrayOf(*char.toTypedArray())

@LanguageFeature
operator fun Byte.Companion.get(vararg byte: Byte) = arrayOf(*byte.toTypedArray())

@LanguageFeature
operator fun Short.Companion.get(vararg short: Short) = arrayOf(*short.toTypedArray())