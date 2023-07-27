package dev.fruxz.ascend.tool.lang

data class LetterRange(override val start: Letter, override val endInclusive: Letter) : ClosedRange<Letter>, Iterable<Letter> {

    override fun iterator(): Iterator<Letter> = buildList {
        for (index in start.ordinal..endInclusive.ordinal) {
            add(Letter.entries[index])
        }
    }.iterator()

}