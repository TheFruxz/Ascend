package dev.fruxz.ascend.tool.math.devision

/**
 * Calculates the greatest common divisor (GCD) of a collection of integers.
 * @see MathDivision.greatestCommonDivisor
 * @author Fruxz
 * @since 2025.7
 */
val Collection<Int>.greatestCommonDivisor: Int
    get() = MathDivision.greatestCommonDivisor(this)