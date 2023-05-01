package dev.fruxz.ascend.tool.math.room.vector

/**
 * This class defines a 'vector' and/or position in a 3-dimensional or 3-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Int].
 * @author Fruxz
 * @since 2023.2
 */
data class IntVector(
    override val x: Int,
    override val y: Int,
    override val z: Int,
) : Vector<Int>
