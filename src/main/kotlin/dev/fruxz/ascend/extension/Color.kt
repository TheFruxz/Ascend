package dev.fruxz.ascend.extension

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import java.awt.Color as AwtColor

/**
 * This Constructor-Like function creates a new [AwtColor], using the [hex]
 * as the color. The [hex] String does *not* have to start with an #, but it
 * is possible.
 * The format #%%%%%% is supported, but #%%% is only partially compatible.
 * @author Fruxz
 * @since 1.0
 */
fun Color(hex: String) = AwtColor(hex.removePrefix("#").let {
    when (it.length) {
        3 -> it.padEnd(6, it.last()) // Support for e.g. #333 color codes
        else -> it
    }.toInt(16)
})

/**
 * This function creates a new [AwtColor] object, using the provided
 * [red], [green], [blue] and [alpha] parameters, wich are by default
 * the values of [this] color object.
 * @author Fruxz
 * @since 1.0
 */
fun AwtColor.copy(red: Int = getRed(), green: Int = getGreen(), blue: Int = getBlue(), alpha: Int = getAlpha()) =
    AwtColor(red, green, blue, alpha)

/**
 * This function creates a hex-color string, using the #%%%%%% format.
 * The parameter [withHash] defines, if the string starts with, or without
 * the # as the prefix.
 * @author Fruxz
 * @since 1.0
 */
fun AwtColor.hexString(withHash: Boolean = true) = when (withHash) {
    true -> "#"
    else -> ""
}.let { prefix ->
    String.format("$prefix%02x%02x%02x", red, green, blue)
}

/**
 * This computational value creates a hex-color string, using the
 * [hexString] function. The property withHash is set to true.
 * @author Fruxz
 * @since 1.0
 */
val AwtColor.hexString: String
    get() = hexString(withHash = true)

/**
 * This computational value creates a rgb-css string, using the
 * [String.format] function.
 * @author Fruxz
 * @since 1.0
 */
val AwtColor.rgbString: String
    get() = String.format("rgb(%d, %d, %d)", red, green, blue)

/**
 * This function creates a [copy] of this color, and moves the color
 * to the [other] color. To define, how far it should be moved to the
 * [other] color, the [strength] is defined, between 0 and 1.
 * 0 equals nothing of the [other] color is applied and 1 equals, that
 * the new color is now [other].
 * @param strength (0.0..1.0) How strong should be the [other] color
 * @author Fruxz
 * @since 1.0
 */
fun AwtColor.mix(other: AwtColor, strength: Double = .5): AwtColor = copy(
    red = (this.red + (other.red - this.red) * strength).roundToInt(),
    green = (this.green + (other.green - this.green) * strength).roundToInt(),
    blue = (this.blue + (other.blue - this.blue) * strength).roundToInt(),
    alpha = (this.alpha + (other.alpha - this.alpha) * strength).roundToInt(),
)

/**
 * This function creates a list (of [steps]-amount elements) containing the
 * colors, used inside a transition from [this] color to the [other] color.
 * The amount of colors is [steps], and [this] + [other] are both included
 * in it. How strong the transition is in the different parts, is defined
 * via the [type], which stores the formula used to compute it.
 * @exception IllegalArgumentException if [steps] is negative (coming from [buildList])
 * @author Fruxz
 * @since 1.0
 */
fun AwtColor.mix(other: AwtColor, steps: Int, type: TransitionType): List<AwtColor> = buildList(capacity = steps) {
    repeat(steps - 1) { index ->
        val step = index + 1
        val x = (step.toDouble() / (steps - 1)) * 2 - 1
        val strength = ((type.formula(x)) / type.maxYDeflection + 1) / 2

        add(index, this@mix.mix(
            other = other,
            strength = strength,
        ))

    }

    add(0, this@mix)

}

/**
 * This function interface defines a formula for a transition.
 * @author Fruxz
 * @since 1.0
 */
fun interface TransitionType {

    /**
     * The formula to compute the progress of the transition
     * @author Fruxz
     * @since 1.0
     */
    fun formula(x: Double): Double

    /**
     * This computational value defines the absolute maximum y
     * of x = -1 and x = 1. Due to the fact, that transitions
     * used from ascend are computed between x = -1 and 1
     * @author Fruxz
     * @since 1.0
     */
    val maxYDeflection: Double
        get() = max(
            a = abs(formula(-1.0)),
            b = abs(formula(1.0)),
        )

    companion object {
        val entries: Array<TransitionType> by lazy { arrayOf(LINEAR, STRONG_IN_OUT, EASY_IN_OUT) }

        val LINEAR = TransitionType { x -> x }
        val STRONG_IN_OUT = TransitionType { x -> x.pow(3) }
        val EASY_IN_OUT = TransitionType { x -> x.pow(3) + x }

    }

}