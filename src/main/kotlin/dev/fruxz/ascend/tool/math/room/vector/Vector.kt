package dev.fruxz.ascend.tool.math.room.vector

/**
 * This class defines a 'vector' and/or position in a 3-dimensional or 3-dimensional-ish view of a mathematical room.
 * @author Fruxz
 * @since 2023.2
 */
interface Vector<T : Number> {

    /**
     * This defines the width of this vector or the [x]-position
     */
    val x: T

    /**
     * This defines the height of this vector or the [y]-position
     */
    val y: T

    /**
     * This defines the depth of this vector or the [z]-position
     */
    val z: T

}