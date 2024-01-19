package dev.fruxz.ascend.extension.data

import dev.fruxz.ascend.extension.container.mapToString
import dev.fruxz.ascend.extension.container.mixedCase
import dev.fruxz.ascend.extension.container.random
import dev.fruxz.ascend.extension.container.repeatRandomElements
import dev.fruxz.ascend.extension.data.RandomTagType.MIXED_CASE
import dev.fruxz.ascend.extension.data.RandomTagType.ONLY_UPPERCASE
import dev.fruxz.ascend.extension.switch
import dev.fruxz.ascend.tool.lang.Letter
import org.jetbrains.annotations.Range
import java.awt.Color
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Creates a new random boolean, which can be true or false.
 * @param random the randomizer to use.
 * @return a random boolean
 * @author Fruxz
 * @since 2023.1
 */
fun randomBoolean(random: Random = Random) = (randomInt(0..1, random) == 1)

/**
 * Creates a new random integer, which is inside the given
 * [range].
 * @param range the range of the random integer (including the boundaries)
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 2023.1
 */
fun randomInt(range: IntRange, random: Random = Random) = random.nextInt(range)

/**
 * Creates a new random integer, which is inside the given [progression]
 * @param progression the possibilities of the random integer
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 2023.1
 */
fun randomInt(progression: Iterable<Int>, random: Random = Random) = progression.random(random = random)

/**
 * Creates a new random long, which is inside the given [range].
 * @param range the range of the random long (including the boundaries)
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 2023.1
 */
fun randomLong(range: LongRange, random: Random = Random) = range.random(random = random)

/**
 * Creates a new random long, which is inside the given [progression]
 * @param progression the possibilities of the random long
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 2023.1
 */
fun randomLong(progression: Iterable<Int>, random: Random = Random) = progression.random(random = random)

/**
 * Generates a random color.
 *
 * @param random The random number generator to use. Default is a new instance of Random.
 * @param red The range of possible values for the red component. Default is 0..255.
 * @param green The range of possible values for the green component. Default is 0..255.
 * @param blue The range of possible values for the blue component. Default is 0..255.
 * @return A randomly generated Color object.
 */
fun randomColor(random: Random = Random, red: Iterable<Int> = 0..255, green: Iterable<Int> = 0..255, blue: Iterable<Int> = 0..255): Color =
	Color(randomInt(red, random), randomInt(green, random), randomInt(blue, random))

@Deprecated("Use randomTAg instead")
@JvmInline value class TagSize(val length: Int)
@Deprecated("Use randomTag instead")
@JvmInline value class TagPrefix(val prefix: CharSequence)

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
@Deprecated("Use generateRandomTag instead", ReplaceWith("generateRandomTag(size, prefix, case, randomizer)"))
fun generateRandomTag(
	size: TagSize = TagSize(5),
	prefix: TagPrefix? = TagPrefix("#"),
	case: RandomTagType = ONLY_UPPERCASE,
	randomizer: Random = Random(Random.nextLong())
) = randomTag(size.length, prefix?.prefix, case, randomizer)

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
fun randomTag(
	size: Int = 5,
	prefix: CharSequence? = "#",
	case: RandomTagType = ONLY_UPPERCASE,
	randomizer: Random = Random(Random.nextLong())
): String {
	val letters = when (case) {
		ONLY_UPPERCASE -> Letter.joinToString().uppercase()
		MIXED_CASE -> Letter.joinToString().mixedCase()
		else -> Letter.joinToString().lowercase()
	}

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
