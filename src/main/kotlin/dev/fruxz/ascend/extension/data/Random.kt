package dev.fruxz.ascend.extension.data

import dev.fruxz.ascend.extension.container.mapToString
import dev.fruxz.ascend.extension.container.mixedCase
import dev.fruxz.ascend.extension.container.repeatRandomElements
import dev.fruxz.ascend.extension.data.RandomTagType.MIXED_CASE
import dev.fruxz.ascend.extension.data.RandomTagType.ONLY_UPPERCASE
import dev.fruxz.ascend.extension.switch
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Creates a new random boolean, which can be true or false.
 * @param random the randomizer to use.
 * @return a random boolean
 * @author Fruxz
 * @since 1.0
 */
fun randomBoolean(random: Random = Random) = (randomInt(1..2, random) == 1)

/**
 * Creates a new random integer, which is inside the given
 * [range].
 * @param range the range of the random integer (including the boundaries)
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 1.0
 */
fun randomInt(range: IntRange, random: Random = Random) = random.nextInt(range)

/**
 * Creates a new random integer, which is inside the given [progression]
 * @param progression the possibilities of the random integer
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 1.0
 */
fun randomInt(progression: Iterable<Int>, random: Random = Random) = progression.shuffled(random).first()

/**
 * Creates a new random long, which is inside the given [range].
 * @param range the range of the random long (including the boundaries)
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 1.0
 */
fun randomLong(range: LongRange, random: Random = Random) = range.shuffled(random).first()

/**
 * Creates a new random long, which is inside the given [progression]
 * @param progression the possibilities of the random long
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 1.0
 */
fun randomLong(progression: Iterable<Int>, random: Random = Random) = progression.shuffled(random).first()

/**
 * Creates a random Tag, which is a combination of a '#' and some
 * random letters & numbers.
 * The template of the Tag is '#[letters & numbers]'.
 * @param size the amount of mixed letters & numbers, which the Tag should have ([size]+1 == tag.length)
 * @param hashtag if true, the Tag will start with a '#'
 * @param tagType the type of the Tag creation (if uppercase, lowercase or mixed-case)
 * @param stackRandomizer the randomizer, which is used to shuffle the characters of the Tag
 * @return a random Tag with the # at the start of the generated Tag
 * @author Fruxz
 * @since 1.0
 */
@Deprecated(message = "This function have been slightly changed, to fit better into the Kotlin ecosystem and enable more customization",
	replaceWith = ReplaceWith(
		"generateRandomTag(size = size, prefix = hashtag.switch(\"#\", \"\"), case = tagType, randomizer = stackRandomizer,)",
		"dev.fruxz.ascend.extension.switch"
	)
)
fun buildRandomTag(size: Int = 5, hashtag: Boolean = true, tagType: RandomTagType = ONLY_UPPERCASE, stackRandomizer: Random = Random(Random.nextLong())): String {
	return generateRandomTag(
		size = size,
		prefix = hashtag.switch("#", ""),
		case = tagType,
		randomizer = stackRandomizer,
	)
}

/**
 * Generates a random tag string.
 *
 * @param size The length of the tag string. Default is 5.
 * @param prefix The prefix to be added to the tag. Default is "#".
 * @param case The case of the tag letters. Default is ONLY_UPPERCASE.
 * @param randomizer The random generator to be used for generating the tag. Default is a new Random instance.
 *
 * @return The generated random tag string.
 */
fun generateRandomTag(
	size: Int = 5,
	prefix: CharSequence = "#",
	case: RandomTagType = ONLY_UPPERCASE,
	randomizer: Random = Random(Random.nextLong())
): String {
	var letters = "abcdefghijklmnopqrstuvwxyz"

	if (case == ONLY_UPPERCASE) letters = letters.uppercase()
	if (case == MIXED_CASE) letters = letters.mixedCase()

	return buildString {
		append(prefix)
		append(
			(letters.toCharArray().toList() + (0..9))
				.mapToString()
				.repeatRandomElements(size, randomizer)
		)
	}
}

enum class RandomTagType {
	ONLY_UPPERCASE,
	ONLY_LOWERCASE,
	MIXED_CASE;
}

@Deprecated("Slightly change the process and updated the name",
	ReplaceWith("generateSeedSet(amount = amount.toInt(), random = random)")
)
fun buildRandomSeeds(amount: Long, random: Random = Random) =
	generateSeedSet(amount = amount.toInt(), random = random)

/**
 * Generates a seed set of random Long values.
 *
 * @param amount the number of random Long values to generate
 * @param random the random number generator to use (defaults to a new instance of Random)
 * @return a Set of random Long values
 */
fun generateSeedSet(amount: Int, random: Random = Random): Set<Long> = buildSet {
	repeat(amount) {
		add(random.nextLong())
	}
}
