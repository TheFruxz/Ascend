package dev.fruxz.ascend.extension

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import java.awt.Color as AwtColor

/**
 * Creates a new [AwtColor] based on the given `rgb` value.
 *
 * @param rgb the RGB value of the color.
 */
fun rgb(rgb: Int) = AwtColor(rgb)

/**
 * Returns an [AwtColor] instance representing the specified RGB color values.
 *
 * @param red The red component of the RGB color. Should be in range 0 to 255.
 * @param green The green component of the RGB color. Should be in range 0 to 255.
 * @param blue The blue component of the RGB color. Should be in range 0 to 255.
 * @return An [AwtColor] instance representing the specified RGB color values.
 */
fun rgb(red: Int, green: Int, blue: Int) = AwtColor(red, green, blue)

/**
 * Creates an [AwtColor] object with the specified red, green, blue, and alpha values.
 *
 * @param red the red component of the color, specified as an integer between 0 and 255
 * @param green the green component of the color, specified as an integer between 0 and 255
 * @param blue the blue component of the color, specified as an integer between 0 and 255
 * @param alpha the alpha component of the color, specified as an integer between 0 and 255
 * @return the [AwtColor] object with the specified RGB values
 */
fun rgb(red: Int, green: Int, blue: Int, alpha: Int) = AwtColor(red, green, blue, alpha)

/**
 * Constructs a new [AwtColor] object based on the specified hue, saturation, and brightness values.
 *
 * @param h the hue component, in the range 0.0f to 1.0f
 * @param s the saturation component, in the range 0.0f to 1.0f
 * @param b the brightness component, in the range 0.0f to 1.0f
 * @return a new [AwtColor] object representing the specified hue, saturation, and brightness values
 */
fun hsb(h: Float, s: Float, b: Float) = AwtColor(h, s, b)

/**
 * This Constructor-Like function creates a new [AwtColor], using the [hex]
 * as the color. The [hex] String does *not* have to start with an #, but it
 * is possible and will be properly handled.
 * Due to the use of [expandHexCode] internally, you can use normal 6-digit
 * color codes, or even the short form with only three digits.
 * @author Fruxz
 * @since 2023.1
 */
fun Color(hex: String) = @OptIn(ExperimentalStdlibApi::class) rgb(rgb = hex
    .removePrefix("#")
    .expandHexCode()
    .hexToInt(HexFormat.UpperCase)
)

/**
 * This function transforms a short-code hex color string to a normal
 * 6-digit hex color string, or returns the input if it already is
 * a 6-digit hex color code.
 * @author Fruxz
 * @since 2023.4
 */
private fun String.expandHexCode() = when (this.length) {
    6 -> this
    3 -> buildString {
        this@expandHexCode.forEach { char ->
            repeat(2) { _ ->
                append(char)
            }
        }
    }
    else -> throw IllegalArgumentException("Your input hex color code '$this' must be 3 (short-code) or 6 numbers long!")
}

/**
 * This function creates a new [AwtColor] object, using the provided
 * [red], [green], [blue] and [alpha] parameters, wich are by default
 * the values of [this] color object.
 * @author Fruxz
 * @since 2023.1
 */
fun AwtColor.copy(red: Int = getRed(), green: Int = getGreen(), blue: Int = getBlue(), alpha: Int = getAlpha()) =
    AwtColor(red, green, blue, alpha)

/**
 * This function creates a hex-color string, using the #%%%%%% format.
 * The parameter [withHash] defines, if the string starts with, or without
 * the # as the prefix.
 * @author Fruxz
 * @since 2023.1
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
 * @since 2023.1
 */
val AwtColor.hexString: String
    get() = hexString(withHash = true)

/**
 * This computational value creates a rgb-css string, using the
 * [String.format] function.
 * @author Fruxz
 * @since 2023.1
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
 * @since 2023.1
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
 * @since 2023.1
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
 * @since 2023.1
 */
fun interface TransitionType {

    /**
     * The formula to compute the progress of the transition
     * @author Fruxz
     * @since 2023.1
     */
    fun formula(x: Double): Double

    /**
     * This computational value defines the absolute maximum y
     * of x = -1 and x = 1. Due to the fact, that transitions
     * used from ascend are computed between x = -1 and 1
     * @author Fruxz
     * @since 2023.1
     */
    val maxYDeflection: Double
        get() = max(
            a = abs(formula(-1.0)),
            b = abs(formula(1.0)),
        )

    enum class Default(private val process: (Double) -> Double) : TransitionType {

        LINEAR({ x -> x }),
        STRONG_IN_OUT({ x -> x.pow(3) }),
        EASY_IN_OUT({ x -> x.pow(3) + x });

        override fun formula(x: Double): Double =
            this.process.invoke(x)

    }

}