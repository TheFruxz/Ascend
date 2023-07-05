package dev.fruxz.ascend.extension.data

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