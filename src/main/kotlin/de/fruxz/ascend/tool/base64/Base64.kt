package de.fruxz.ascend.tool.base64

import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.io.encoding.Base64 as KotlinBase64

/**
 * This object provides methods to encode and decode strings to and from Base64.
 * @author Fruxz
 * @since 1.0
 */
@Suppress("UnnecessaryOptInAnnotation") // This is necessary for the opt-in
@OptIn(ExperimentalEncodingApi::class)
@Deprecated("This API is replaced with Kotlin's Base64 API", ReplaceWith("Base64", "kotlin.io.encoding.Base64"))
object Base64 {

    /**
     * From String to Base64
     * @author Fruxz
     * @since 1.0
     */
    @Deprecated(
        message = "Use Kotlin Library instead",
        replaceWith = ReplaceWith("Base64.encode(input.encodeToByteArray())", "kotlin.io.encoding.Base64"),
    )
    fun encodeToString(input: String): String = KotlinBase64.encode(input.encodeToByteArray())

    /**
     * From Base64 to String
     * @author Fruxz
     * @since 1.0
     */
    @Deprecated(
        message = "Use Kotlin Library instead",
        replaceWith = ReplaceWith("Base64.decode(input.encodeToByteArray()).decodeToString()", "kotlin.io.encoding.Base64"),
    )
    fun decodeToString(input: String): String = KotlinBase64.decode(input.encodeToByteArray()).decodeToString()

}