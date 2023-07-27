package dev.fruxz.ascend.tool.lang

import java.util.*

/**
 * An enum class that represents the english alphabet and provide quick tools to work with it.
 * @author Fruxz
 * @since 1.0
 */
enum class Letter : Comparable<Letter> {

    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    /**
     * Returns this letter as a lowercase string using [locale].
     */
    fun lowercase(locale: Locale = Locale.getDefault()) =
        this.name.lowercase(locale)

    /**
     * Returns this letter as an uppercase string using [locale].
     */
    fun uppercase(locale: Locale = Locale.getDefault()) =
        this.name.uppercase(locale)

    operator fun rangeTo(other: Letter) =
        LetterRange(this, other)

    operator fun rangeUntil(other: Letter) =
        LetterRange(this, entries[other.ordinal - 1])

    companion object {

        /**
         * Regular Expression to match uppercase letters.
         */
        val regexUppercase = Regex("[A-Z]")

        /**
         * Regular expression pattern to match lowercase letters.
         */
        val regexLowercase = Regex("[a-z]")

        /**
         * Regular expression pattern that matches any alphabetical character,
         * regardless of case sensitivity.
         *
         * The pattern matches any single character from a-z or A-Z.
         * It is case-insensitive, meaning it matches both lowercase and uppercase letters.
         *
         * Example usage:
         * ```
         * val input = "Hello World"
         * val matches = regexBothCases.findAll(input)
         * for (match in matches) {
         *     println(match.value)
         * }
         * ```
         */
        val regexBothCases = Regex("[a-zA-Z]")

        /**
         * Returns a string representation of the entries in the `Letter` enum
         * joined together using the specified separator, prefix, and postfix.
         * The letters are converted to uppercase based on the specified locale.
         *
         * @param separator The character sequence used to separate the entries. Default is an empty string.
         * @param prefix The character sequence to prepend to the joined string. Default is an empty string.
         * @param postfix The character sequence to append to the joined string. Default is an empty string.
         * @param locale The locale used to convert the letters to uppercase. Default is the system's default locale.
         * @return A string representation of the entries joined together.
         */
        fun joinToString(separator: CharSequence = "", prefix: CharSequence = "", postfix: CharSequence = "", locale: Locale = Locale.getDefault()) =
            Letter.entries.joinToString(separator, prefix, postfix) { it.uppercase(locale) }

        /**
         * Concatenates the string representations of the current Digit object and the given Digit object.
         *
         * @param number The Digit object to be concatenated.
         * @return The concatenated string representation of the two Digit objects.
         */
        operator fun plus(number: Digit.Companion) = joinToString() + number.joinToString()

    }

}