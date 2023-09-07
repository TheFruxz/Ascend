package dev.fruxz.ascend.extension.math

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.annotation.RefactoringCandidate

@ExperimentalAscendApi
infix fun Long.intPercentageOf(denominator: Long) = intPercentageOf(this, denominator)

@ExperimentalAscendApi
fun intPercentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()
