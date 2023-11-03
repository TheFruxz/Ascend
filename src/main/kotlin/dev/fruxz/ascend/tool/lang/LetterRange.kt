package dev.fruxz.ascend.tool.lang

/**
 * This data class defines a range of two [Letter]s of the english alphabet, in alphabetical order.
 * @param start the first letter
 * @param endInclusive the last letter
 * @author Fruxz
 * @since 2023.5
 */
data class LetterRange(override val start: Letter, override val endInclusive: Letter) : ClosedRange<Letter>, Iterable<Letter> {

    override fun iterator(): Iterator<Letter> = buildList {
        for (index in start.ordinal..endInclusive.ordinal) {
            add(Letter.entries[index])
        }
    }.iterator()

}