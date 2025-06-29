package dev.fruxz.ascend.tool

import java.util.Base64

object Base64 {

    /**
     * From String to Base64
     * @author Fruxz
     * @since 1.0
     */
    fun encodeToString(input: String): String = Base64.getEncoder().encodeToString(input.encodeToByteArray())

    /**
     * From Base64 to String
     * @author Fruxz
     * @since 1.0
     */
    fun decodeToString(input: String): String = Base64.getDecoder().decode(input.encodeToByteArray()).decodeToString()

}