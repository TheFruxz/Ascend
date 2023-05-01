package dev.fruxz.ascend.tool.math.room.point

/**
 * This class defines a 'position' in a 2-dimensional or 2-dimensional-ish view of a mathematical room.
 * @author Fruxz
 * @since 2023.2
 */
interface Point<T : Number> {

    /**
     * Defines the [x] position (or width) of this point.
     */
    val x: T

    /**
     * Defines the [y] position (or height) of this point.
     */
    val y: T

}
