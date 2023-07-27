package dev.fruxz.ascend.tool.lang

/**
 * Represents a number.
 * @author Fruxz
 * @since 1.0
 */
enum class Digit(val number: Byte) {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    operator fun rangeTo(other: Int): IntRange = number..other

    operator fun rangeUntil(other: Int): IntRange = number until other

    companion object {

        /**
         * Joins the elements of the `Digit` enum into a single string, using the provided separator,
         * prefix, and postfix. The elements are converted to strings using their `number` property.
         *
         * @param separator The string to use as a separator between each element. Default is an empty string.
         * @param prefix The string to prepend to the resulting string. Default is an empty string.
         * @param postfix The string to append to the resulting string. Default is an empty string.
         * @return The resulting string after joining the elements of `Digit` enum.
         */
        fun joinToString(separator: CharSequence = "", prefix: CharSequence = "", postfix: CharSequence = "") =
            Digit.values().joinToString(separator, prefix, postfix) { it.number.toString() }

        /**
         * Concatenates the current Letter object with another Letter object
         *
         * @param letter The Letter object to be concatenated with the current Letter
         * @return A new String obtained by joining the elements of the current Letter and the provided Letter
         */
        operator fun plus(letter: Letter.Companion) = joinToString() + letter.joinToString()

    }

}