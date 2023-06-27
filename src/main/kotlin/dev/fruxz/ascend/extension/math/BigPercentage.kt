package dev.fruxz.ascend.extension.math

import dev.fruxz.ascend.annotation.ExperimentalAscendApi

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(message = "The name of this function may change in the future!")
@ExperimentalAscendApi
infix fun Long.outOf(denominator: Long) = intPercentageOf(this, denominator)

fun intPercentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()
