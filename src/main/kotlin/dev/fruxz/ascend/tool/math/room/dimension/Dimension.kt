package dev.fruxz.ascend.tool.math.room.dimension

/**
 * This class defines a 'dimension' and/or scale in a 2-dimensional or 2-dimensional-ish view of a mathematical room.
 * @author Fruxz
 * @since 2023.2
 */
interface Dimension<T : Number> {

    /**
     * Defines the size of the width of this dimension.
     */
    val x: T

    /**
     * Defines the size of the height of this dimension.
     */
    val y: T

}