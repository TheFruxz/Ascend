package dev.fruxz.ascend.tool

import java.util.Base64

/**
 * Utility object for Base64 encoding and decoding.
 * Provides methods to convert strings to Base64 format and vice versa.
 *
 * @author Fruxz
 * @since 2025.6
 */
object Base64 {

    /**
     * From String to Base64
     * @author Fruxz
     * @since 2025.6
     */
    fun encodeToString(input: String): String = Base64.getEncoder().encodeToString(input.encodeToByteArray())

    /**
     * From Base64 to String
     * @author Fruxz
     * @since 2025.6
     */
    fun decodeToString(input: String): String = Base64.getDecoder().decode(input.encodeToByteArray()).decodeToString()

}