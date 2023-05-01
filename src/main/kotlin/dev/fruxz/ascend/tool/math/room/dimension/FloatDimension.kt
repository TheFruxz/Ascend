package dev.fruxz.ascend.tool.math.room.dimension

/**
 * This class defines a 'dimension' and/or scale in a 2-dimensional or 2-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Float].
 * @author Fruxz
 * @since 2023.2
 */
data class FloatDimension(
    override val x: Float,
    override val y: Float,
) : Dimension<Float>
