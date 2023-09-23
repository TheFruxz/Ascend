package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.annotation.LanguageFeature

@LanguageFeature
operator fun Int.Companion.get(vararg int: Int): Array<Int> = int.toTypedArray()

@LanguageFeature
operator fun Long.Companion.get(vararg long: Long): Array<Long> = long.toTypedArray()

@LanguageFeature
operator fun Float.Companion.get(vararg float: Float): Array<Float> = float.toTypedArray()

@LanguageFeature
operator fun Double.Companion.get(vararg double: Double): Array<Double> = double.toTypedArray()

@LanguageFeature
operator fun Boolean.Companion.get(vararg boolean: Boolean): Array<Boolean> = boolean.toTypedArray()

@LanguageFeature
operator fun Char.Companion.get(vararg char: Char): Array<Char> = char.toTypedArray()

@LanguageFeature
operator fun Byte.Companion.get(vararg byte: Byte): Array<Byte> = byte.toTypedArray()

@LanguageFeature
operator fun Short.Companion.get(vararg short: Short): Array<Short> = short.toTypedArray()