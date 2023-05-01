package dev.fruxz.ascend.tool.math.room.vector

/**
 * This class defines a 'vector' and/or position in a 3-dimensional or 3-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Float].
 * @author Fruxz
 * @since 2023.2
 */
data class FloatVector(
    override val x: Float,
    override val y: Float,
    override val z: Float,
) : Vector<Float>
