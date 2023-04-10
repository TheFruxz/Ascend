package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.extension.data.randomBoolean
import java.util.*
import kotlin.random.Random

/**
 * Replaces all occurrences of the given [Map.keys] with the given [Map.values] in this string.
 * @param map the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(map: Map<out Any?, Any?>, ignoreCase: Boolean = false): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("$key", "$value", ignoreCase)
	}

	return out
}

/**
 * Replaces all occurrences of the given [Pair.first] with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(vararg pairs: Pair<Any?, Any?>) = replace(mapOf(*pairs))

/**
 * Replaces all occurrences of the given [Pair.first] with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(pairs: Collection<Pair<Any?, Any?>>, ignoreCase: Boolean = false) =
	replace(map = pairs.toMap(), ignoreCase = ignoreCase)

/**
 * Replaces all occurrences of the given [Map.keys] surrounded by a `[` and a `]` with the given [Map.values] in this string.
 * @param map the map of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] surrounded by a `[` and a `]` replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(map: Map<out Any?, Any?>, ignoreCase: Boolean = false): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("[$key]", "$value", ignoreCase)
	}

	return out
}

/**
 * Replaces all occurrences of the given [Pair.first] surrounded by a `[` and a `]` with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] surrounded by a `[` and a `]` replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(vararg pairs: Pair<Any?, Any?>) = replaceVariables(mapOf(*pairs))

/**
 * Replaces all occurrences of the given [Pair.first] surrounded by a `[` and a `]` with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] surrounded by a `[` and a `]` replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(pairs: Collection<Pair<Any?, Any?>>, ignoreCase: Boolean = false) =
	replaceVariables(pairs.toMap(), ignoreCase)

/**
 * Generates a new complete empty String without any content or any characters.
 * @return a new empty String
 * @author Fruxz
 * @since 1.0
 */
fun emptyString() = ""

/**
 * This function uses the [UUID.fromString] function to generate the
 * UUID from the given [String] and returns it.
 * @throws IllegalArgumentException if the given [String] is not a valid UUID
 * @return the UUID generated from the given [String]
 * @author Fruxz
 * @since 1.0
 */
@Throws(IllegalArgumentException::class)
fun String.toUUID() = UUID.fromString(this)!!

/**
 * Replaces the first [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [startsWith] the [oldValue].
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @see replaceFirst
 * @author Fruxz
 * @since 1.0
 */
fun String.replacePrefix(oldValue: String, newValue: String, ignoreCase: Boolean = false) =
	if (startsWith(oldValue, ignoreCase)) {
		replaceFirst(oldValue, newValue, ignoreCase)
	} else
		this

/**
 * Replaces the last [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [endsWith] the [oldValue].
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceSuffix(oldValue: String, newValue: String, ignoreCase: Boolean = false) =
	if (endsWith(oldValue, ignoreCase)) {
		split(oldValue).dropLast(1).joinToString(oldValue) + newValue
	} else
		this

/**
 * Replaces the first and last [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [startsWith] or/and [endsWith] the [oldValue].
 * If only one side (start or end) starts or ends with the [oldValue],
 * then only this will get replaced, but there is no error, that the other
 * side is not found!
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @see replacePrefix
 * @see replaceSuffix
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceSurrounding(oldValue: String, newValue: String, ignoreCase: Boolean = false) =
	replacePrefix(oldValue, newValue, ignoreCase)
		.replaceSuffix(oldValue, newValue, ignoreCase)

/**
 * This function returns the string, but with mixed case set.
 * If [randomized] is true (default = true), then the case will be randomized, but
 * if it is false, the case would be set to cycle around between low-up-low-up...
 * @param randomized if the case should be randomized
 * @param random the randomizer to use
 * @return the string with mixed case
 * @author Fruxz
 * @since 1.0
 */
fun String.mixedCase(randomized: Boolean = true, random: Random = Random(Random.nextLong())) =
	toCharArray().withIndex().joinToString(separator = "") { (index, char) ->
		if (randomized && randomBoolean(random) || index % 2 == 0) { // More forced uniqueness because of next seed
			char.uppercase()
		} else
			char.lowercase()
	}

/**
 * This function splits the string into a list of strings, by the points
 * where the [spliterator] matches.
 * Due to the fact, that the kotlin [split] function does not support,
 * to keep the spliterator itself in the list, this function was created.
 * @param spliterator the spliterator to split the string by
 * @param containSpliterator if the spliterator should be contained in the list
 * @author Fruxz
 * @since 1.0
 */
fun String.split(
	spliterator: Regex,
	containSpliterator: Boolean,
): List<String> = when (containSpliterator) {
	false -> split(spliterator)
	true -> {
		split(spliterator)
			.zip(spliterator.findAll(this).map { it.value }.asIterable() + "") { a, b ->
				listOf(a, b)
			}
			.flatten()
			.dropLast(1)
	}
}

/**
 * This function splits the string into a list of strings, where
 * 'zones' are detected. Zones are defined by being surrounded by
 * the [regexMatch], which is stored inside a big regex: `(?<=$regexMatch)[^$regexMatch]*(?=$regexMatch)`.
 * The matching symbols are stored outside the matched zones.
 * With [onlyBiZones] you can limit, that they have to be surrounded on both sides, or that a single side already is enough.
 *
 * ***ATTENTION: [regexMatch] is placed inside a regex string, so you have to consider this!***
 * @author Fruxz
 * @since 1.0
 */
fun String.splitZones(
	regexMatch: CharSequence = "\"",
	keepSpliterator: Boolean = true,
	onlyBiZones: Boolean = true
): List<String> {
	val result = split(
		"(?<=$regexMatch)[^$regexMatch]*(?=$regexMatch)".toRegex(),
		keepSpliterator
	)

	return when (onlyBiZones) {
		false -> result
		true -> result.dropLast(3) + result.takeLast(3).joinToString("")
	}

}

/**
 * This function splits the string by [argumentSpliterator] and joins the arguments, which are surrounded by the [chunkMarker].
 * Example: 'This is "a demo" string' -> ['This', 'is', 'a demo', 'string']
 * @author Fruxz
 * @since 1.0
 */
fun String.joinArgumentChunks(chunkMarker: String = "\"", argumentSpliterator: String = " "): List<String> = buildList {
	var isQuoted = false
	val current = StringBuilder()
	val strings = (this@joinArgumentChunks.split(argumentSpliterator).takeIf { this@joinArgumentChunks.isNotBlank() }.orEmpty())

	for (string in strings) {
		if (string.startsWith(chunkMarker)) {
			isQuoted = true
			current.append(string.removePrefix(chunkMarker))
		} else if (string.endsWith(chunkMarker)) {
			if (isQuoted) current.append(" ")

			isQuoted = false
			current.append(string.removeSuffix(chunkMarker))
			add(current.toString())
			current.clear()
		} else if (isQuoted) {
			current.append(" ").append(string)
		} else {
			add(string)
		}
	}

	if (current.isNotEmpty()) addAll((chunkMarker + current).split(" "))

}
