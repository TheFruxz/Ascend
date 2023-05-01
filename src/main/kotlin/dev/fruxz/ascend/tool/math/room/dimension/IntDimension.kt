package dev.fruxz.ascend.tool.math.room.dimension

/**
 * This class defines a 'dimension' and/or scale in a 2-dimensional or 2-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Int].
 * @author Fruxz
 * @since 2023.2
 */
data class IntDimension(
    override val x: Int,
    override val y: Int,
) : Dimension<Int>
