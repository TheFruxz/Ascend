package de.fruxz.ascend.extension.container

operator fun Int.Companion.get(vararg int: Int) = arrayOf(*int.toTypedArray())

operator fun Long.Companion.get(vararg long: Long) = arrayOf(*long.toTypedArray())

operator fun Float.Companion.get(vararg float: Float) = arrayOf(*float.toTypedArray())

operator fun Double.Companion.get(vararg double: Double) = arrayOf(*double.toTypedArray())

operator fun Boolean.Companion.get(vararg boolean: Boolean) = arrayOf(*boolean.toTypedArray())

operator fun Char.Companion.get(vararg char: Char) = arrayOf(*char.toTypedArray())

operator fun Byte.Companion.get(vararg byte: Byte) = arrayOf(*byte.toTypedArray())

operator fun Short.Companion.get(vararg short: Short) = arrayOf(*short.toTypedArray())