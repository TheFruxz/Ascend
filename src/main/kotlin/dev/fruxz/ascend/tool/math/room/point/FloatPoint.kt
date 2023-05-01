package dev.fruxz.ascend.tool.math.room.point

/**
 * This class defines a 'position' in a 2-dimensional or 2-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Float].
 * @author Fruxz
 * @since 2023.2
 */
data class FloatPoint(
    override val x: Float,
    override val y: Float,
) : Point<Float>
