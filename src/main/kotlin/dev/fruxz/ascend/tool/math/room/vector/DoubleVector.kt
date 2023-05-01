package dev.fruxz.ascend.tool.math.room.vector

/**
 * This class defines a 'vector' and/or position in a 3-dimensional or 3-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Double].
 * @author Fruxz
 * @since 2023.2
 */
data class DoubleVector(
    override val x: Double,
    override val y: Double,
    override val z: Double,
) : Vector<Double>
