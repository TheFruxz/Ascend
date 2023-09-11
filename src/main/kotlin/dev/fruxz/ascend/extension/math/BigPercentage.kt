package dev.fruxz.ascend.extension.math

import dev.fruxz.ascend.annotation.ExperimentalAscendApi

@ExperimentalAscendApi
infix fun Long.intPercentageOf(denominator: Long) = intPercentageOf(this, denominator)

@ExperimentalAscendApi
fun intPercentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()
