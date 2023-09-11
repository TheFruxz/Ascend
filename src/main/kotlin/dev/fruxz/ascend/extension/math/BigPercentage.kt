package dev.fruxz.ascend.extension.math

import dev.fruxz.ascend.annotation.ExperimentalAscendApi

@ExperimentalAscendApi
fun intPercentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()
