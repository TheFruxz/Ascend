package dev.fruxz.ascend.tool.math.decimal

/**
 * This object helps to easily calculate percentages in
 * bigger number scales.
 * @author Fruxz
 * @since 2023.4
 */
object BigPercentage {

    /**
     * Computes the percentage of the given [numerator] and [denominator].
     * @param numerator the number, which represents the percentage
     * @param denominator the number, which represents the total
     */
    fun percentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()

}