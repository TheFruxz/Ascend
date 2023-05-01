package dev.fruxz.ascend.tool.math.room.vector

/**
 * This class defines a 'vector' and/or position in a 3-dimensional or 3-dimensional-ish view of a mathematical room.
 * Coordinates defined via a [Long].
 * @author Fruxz
 * @since 2023.2
 */
data class LongVector(
    override val x: Long,
    override val y: Long,
    override val z: Long,
) : Vector<Long>
