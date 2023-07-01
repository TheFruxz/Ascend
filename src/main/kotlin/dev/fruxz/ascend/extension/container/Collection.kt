package dev.fruxz.ascend.extension.container

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.collection.Paged
import java.util.*
import kotlin.NoSuchElementException
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Creates a new [ArrayList]<[T]> object containing all elements from the calling Collection object.
 *
 * @return the new [ArrayList] object containing all elements from the calling Collection object.
 * @param T the type of elements in the calling Collection object
 * @param C the type of calling Collection object, must be a subtype of Collection<[T]>
 */
fun <T, C : Collection<T>> C.toArrayList(): ArrayList<T> = ArrayList(this)

/**
 * Creates a new [ArrayList] object containing the elements of the array.
 *
 * @return The [ArrayList] object created from the array.
 *
 * @param T The type of elements in the array.
 *
 * @since 1.0-BETA-5 (preview)
 */
fun <T> Array<out T>.toArrayList(): ArrayList<out T> = toList().toArrayList()

/**
 * Repeats the elements in the given collection a specified number of times,
 * adding random elements from the original collection each time.
 *
 * @param times The number of times to repeat the elements.
 * @param random The random number generator to use for selecting random elements.
 *               If not provided, a default random number generator is used.
 *
 * @return A string that contains the repeated elements, including the randomly added elements.
 */
fun <T, C : Collection<T>> C.repeatRandomElements(times: Int, random: Random = Random): String = buildString {
	repeat(times) { append(this@repeatRandomElements.random(random)) }
}

/**
 * Generates a string by stacking the elements of the array randomized [times] times.
 *
 * @param times The amount of repeats.
 * @param random (Optional) The random number generator to use. Default is a new instance of Random.
 * @return The generated string with repeated random elements.
 */
fun <T> Array<out T>.repeatRandomElements(times: Int, random: Random = Random): String = buildString {
	repeat(times) { append(this@repeatRandomElements.random(random)) }
}

/**
 * Stacks the unique elements of the iterable [times] times in a random order.
 *
 * @param times the number of times to repeat the elements
 * @param random the optional Random object to use for randomness
 * @return a string representing the stacked unique elements in a random order
 */
fun <T, C : Iterable<T>> C.repeatUniqueRandomElements(times: Int, random: Random = Random): String =
	shuffled(random).take(times).joinToString(separator = "")

/**
 * Stacks the elements and generates a unique random string
 * using the specified number of repeats.
 *
 * @param times the number of repeats
 * @param random the random number generator
 * @return the generated unique random string
 */
fun <T> Array<out T>.repeatUniqueRandomElements(times: Int, random: Random = Random): String =
	toList().repeatUniqueRandomElements(times, random)

/**
 * This function creates a list of objects of type [T],
 * by invoking the specified [generator] function for each entry.
 *
 * @param T the inner containing data type
 * @param size the size of the list
 * @param generator the function used to generate an entry
 * @return the list of [T] objects
 */
inline fun <T> constructListOf(size: Int, generator: (Int) -> T): List<T> = List(size) { generator(it) }

/**
 * Returning the first object of the collection [C]
 * @author Fruxz
 * @since 1.0
 */
val <T, C : Iterable<T>> C.first: T
	get() = first()

/**
 * Returning the first object of the array
 * @author Fruxz
 * @since 1.0
 */
val <T> Array<out T>.first: T
	get() = first()

/**
 * Returning the second object of the collection [C]
 * @author Fruxz
 * @since 1.0
 */
val <T, C : Iterable<T>> C.second: T
	get() = elementAt(1)

/**
 * Returning the second object of the array
 * @author Fruxz
 * @since 1.0
 */
val <T> Array<out T>.second: T
	get() = elementAt(1)

/**
 * Returning the third object of the collection [C]
 * @author Fruxz
 * @since 1.0
 */
val <T, C : Iterable<T>> C.third: T
	get() = elementAt(2)

/**
 * Returning the third object of the array
 * @author Fruxz
 * @since 1.0
 */
val <T> Array<out T>.third: T
	get() = elementAt(2)

/**
 * Returning the last object of the collection [C]
 * @author Fruxz
 * @since 1.0
 */
val <T, C : Iterable<T>> C.last: T
	get() = last()

/**
 * Returns the last object of the array
 * @author Fruxz
 * @since 1.0
 */
val <T> Array<out T>.last: T
	get() = last()

/**
 * This function returns the item at the [index] position in an Iterable collection of type [T],
 * or null if the index is out of bounds. When the [overflow] parameter is set to true,
 * and the index is out of bounds, the function calculates the modulus of the index and the size
 * of the collection to return an item from the start.
 *
 * @param T The generic type indicating the type of elements in this Iterable.
 *
 * @param index The target index in the Iterable. It determines the item to return.
 *
 * @param overflow A boolean flag setting the behavior when [index] is out of bounds.
 * When set to true, it adjusts the index to fit into the collection bounds.
 * When set to false or not provided, it returns null when [index] is out of bounds.
 *
 * @return The element at the [index] in the Iterable of type [T] or null when the index is out of bounds
 * and [overflow] is false.
 *
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.getOrNull(index: Int, overflow: Boolean = false): T? {
	return when {
		index in 0 until count() -> elementAtOrNull(index)
		overflow -> elementAtOrNull(index % count())
		else -> null
	}
}

/**
 * This function returns the object at the [index] of the collection [C]<[T]>,
 * or if the index is out of bounds and [overflow] is true, it will go back to
 * the start of the collection and return the object at the [index] minus the
 * size of the collection and returns that object.
 * @param T the inner containing data type
 * @param C the collection type
 * @param index the index of the searched object
 * @param overflow if the index is out of bounds, should it go back to the start of the collection
 * @return the object at the [index] of the collection [C]<[T]>
 * @author Fruxz
 * @since 1.0
 */
@Throws(NoSuchElementException::class)
fun <T, C : Iterable<T>> C.get(index: Int, overflow: Boolean = false): T =
	getOrNull(index, overflow) ?: throw NoSuchElementException("Index $index is not inside lists 0..${count() - 1} content and overflow is disabled!")

/**
 * Creates a sublist of the [intRange]
 * @return a new sublist of the [C]
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : Iterable<T>> C.take(intRange: IntRange): List<T> =
	toList().subList(intRange)

/**
 * Creates a sublist of the [intRange]
 * @return a new sublist of the [Array]<[T]>
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.take(intRange: IntRange): List<T> =
	toList().subList(intRange)

/**
 * This function returns a small list of [T] objects, that
 * are in a simulated page, which is created by the [chunkSize]
 * and the [paged] number.
 * If the requested page is out of range, it will return the last non-empty page.
 * @param chunkSize the size of each individual page
 * @return the list of [T] objects contained in the page
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : Iterable<T>> C.partitionByPage(chunkSize: Int): Paged<T> =
	Paged(chunkSize, this)

/**
 * This function returns a small list of [T] objects, that
 * are in a simulated segment (chunk) of the data, which is determined by the [chunkSize].
 * The function will return the first chunk of size [chunkSize].
 * If the requested [chunkSize] is larger than the total size of the list, it will return the whole list.
 * @param chunkSize the size of the desired output list segment
 * @return the list segment of [T] objects with the specified [chunkSize]
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.partitionByPage(chunkSize: Int): Paged<T> =
	toList().partitionByPage(chunkSize)

/**
 * This function returns, if the current [Collection]
 * has some duplicates in it. The decision is made
 * by the [distinct] function.
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : Iterable<T>> C.hasDuplicates(): Boolean =
	count() > distinct().size

/**
 * This function returns, if the current [Collection]
 * has some duplicates in it. The decision is made
 * by the [distinct] function.
 * @param process defines the value, with which object the differentiation is made
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C : Iterable<T>, K> C.hasDuplicates(process: (T) -> K): Boolean =
	count() > distinctBy(process).size

/**
 * This function returns, if the current [Array]
 * has some duplicates in it. The decision is made
 * by the [distinct] function.
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.hasDuplicates(): Boolean =
	size > distinct().size

/**
 * This function returns, if the current [Array]
 * has some duplicates in it. The decision is made
 * by the [distinct] function.
 * @param process defines the value, with which object the differentiation is made
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, K> Array<T>.hasDuplicates(process: (T) -> K): Boolean =
	size > distinctBy(process).size

/**
 * This function returns the average duration, out of the multiple duration
 * provided by the [Collection].
 * @return the average duration, determined by milliseconds
 * @author Fruxz
 * @since 1.0
 */
fun <C : Iterable<Duration>> C.average(): Duration =
	map(Duration::inWholeMilliseconds).sum().div(count()).milliseconds

/**
 * This function returns the summary duration, out of the multiple duration
 * provided by the [Collection].
 * @return the summary duration, determined by milliseconds
 * @author Fruxz
 * @since 1.0
 */
fun <C : Iterable<Duration>> C.sum(): Duration =
	map(Duration::inWholeMilliseconds).sum().milliseconds

/**
 * This function returns the largest duration, out of the multiple durations
 * provided by the [Collection].
 * @return the largest duration, determined by milliseconds
 * @author Fruxz
 * @since 1.0
 */
fun <C : Iterable<Duration>> C.max(): Duration =
	map(Duration::inWholeMilliseconds).maxOf { it }.milliseconds

/**
 * This function returns the smallest duration, out of the multiple durations
 * provided by the [Collection].
 * @return the smallest duration, determined by milliseconds
 * @author Fruxz
 * @since 1.0
 */
fun <C : Iterable<Duration>> C.min(): Duration =
	map(Duration::inWholeMilliseconds).minOf { it }.milliseconds

/**
 * This function returns, if the current [Iterable] contains the
 * [element] provided. (compared with [ignoreCase] in mind)
 */
fun <C : Iterable<String>> C.contains(element: String, ignoreCase: Boolean = false) =
	any { it.equals(element, ignoreCase) }

/**
 * This function returns, if the current [Iterable] contains all
 * the [elements] provided. (compared with [ignoreCase] in mind)
 */
fun <C : Iterable<String>> C.containsAll(elements: Iterable<String>, ignoreCase: Boolean = false) =
	elements.all { contains(it, ignoreCase) }

/**
 * This function applies the split function of strings to collections.
 * A list contains every element, until the [predicate] is true. If it is
 * true, the list is stored, a new list for the following elements is created
 * and the indicating element itself is getting dropped.
 * ***example: [1, 2, 3, 4, 5, 6, 7, 8, 9].splitBy { it % 3 == 0 } -> [[1, 2], [4, 5], [7, 8]]***
 * @param predicate the predicate, which indicates, if the current element
 * should be dropped and the list should be stored
 * @return the list of lists, which are created by the [predicate]
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C : Iterable<T>> C.splitBy(predicate: (T) -> Boolean): List<List<T>> {
	val output = mutableListOf<List<T>>()
	var currentFragment = mutableListOf<T>()

	forEach {
		if (predicate(it)) {
			output.add(currentFragment.toList())
			currentFragment = mutableListOf()
		} else {
			currentFragment.add(it)
		}
	}

	if (currentFragment.isNotEmpty()) output.add(currentFragment.toList())

	return output
}

/**
 * This function uses the [Collection.distinct] function and
 * returns the result-list as a [Set], by using the [toSet] function.
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : Iterable<T>> C.distinctSet() = distinct().toSet()

/**
 * This function uses the [Array.distinct] function and
 * returns the result-list as a [Set], by using the [toSet] function.
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.distinctSet() = distinct().toSet()

/**
 * This function uses the [Collection.distinctBy] function and
 * returns the result-list as a [List], by using the [toList] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C : Iterable<T>, O> C.distinctSetBy(process: (T) -> O) = distinctBy(process).toSet()

/**
 * This function uses the [Array.distinctBy] function and
 * returns the result-list as a [List], by using the [toList] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, O> Array<T>.distinctSetBy(process: (T) -> O) = distinctBy(process).toSet()

/**
 * This function uses the [Collection.forEach] function, but only on every
 * element, which is not null.
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C : Iterable<T?>> C.forEachNotNull(process: (T & Any) -> Unit) = forEach { if (it != null) process(it) }

/**
 * This function uses the [Array.forEach] function, but only on every
 * element, which is not null.
 * @author Fruxz
 * @since 1.0
 */
inline fun <T> Array<T?>.forEachNotNull(process: (T & Any) -> Unit) = forEach { if (it != null) process(it) }

/**
 * Applies the given [builder] function to each element in the collection and returns a new list
 * containing the non-null results of the builder function.
 *
 * @param C the type of the collection.
 * @param I the type of the input elements in the collection.
 * @param O the type of the output elements in the resulting list.
 * @param builder the function to apply to each element in the collection.
 * @return a new list containing the non-null results of the builder function.
 */
inline fun <C : Iterable<I>, I, O> C.flatMapNotNull(builder: (I) -> Iterable<O?>): List<O & Any> = buildList {
	this@flatMapNotNull.forEach { t ->
		builder.invoke(t).forEachNotNull { o ->
			add(o)
		}
	}
}

/**
 * Flattens an iterable of iterables, removing any null values.
 *
 * @param T the type of the iterable of iterables
 * @param O the type of the elements in the iterables
 * @return a new iterable containing all non-null elements from the original iterables
 */
fun <T : Iterable<O>, O> Iterable<T>.flattenNotNull() = flatMapNotNull { it }

/**
 * Concatenates the results of applying the given transform function
 * to each element of the original array and filters out any null values.
 * The elements are flattened into a single list.
 *
 * @param builder the transform function to apply to each element.
 *                It takes an element of the original array as input and returns
 *                an Iterable of transformed elements.
 * @return a List containing the non-null transformed elements.
 */
inline fun <I, O> Array<I>.flatMapNotNull(builder: (I) -> Iterable<O?>): List<O & Any> = buildList {
	this@flatMapNotNull.forEach { t ->
		builder.invoke(t).forEachNotNull { o ->
			add(o)
		}
	}
}

/**
 * Flattens an array of iterables, removing any null values.
 *
 * This method takes an array of iterables and flattens it into a single list, discarding any null values along the way.
 * The resulting list contains the non-null values from the original iterables in the same order as they appeared. If
 * an iterable contains null elements, they are skipped and not included in the resulting list.
 *
 * @param T the type of iterable elements in the array
 * @param O the type of elements in the iterables
 * @return a list containing the non-null elements from the original iterables in the order they appeared
 */
fun <T : Iterable<O>, O> Array<T>.flattenNotNull() = flatMapNotNull { it }

/**
 * Returns a new list containing the elements of the original list if it is not null,
 * or an empty list if the original list is null.
 *
 * @return a new list containing the elements of the original list or an empty list
 *
 * @param T the type of elements in the list
 */
fun <T> List<T>?.takeOrEmpty() = this ?: emptyList()

/**
 * Returns the given set if it is not null, otherwise returns an empty set.
 *
 * @param T the type of elements in the set
 * @return the given set if not null, otherwise an empty set
 */
fun <T> Set<T>?.takeOrEmpty() = this ?: emptySet()

/**
 * Returns either the given array or an empty array if the given array is null.
 *
 * @return The given array if it is not null, otherwise an empty array.
 * @param T The type of elements in the array.
 */
inline fun <reified T> Array<T>?.takeOrEmpty() = this ?: emptyArray()

/**
 * Returns the current map if not null, otherwise returns an empty map.
 *
 * @return The current map if not null, otherwise an empty map.
 * @receiver The map to take or an empty map.
 * @param K The type of the map keys.
 * @param V The type of the map values.
 */
fun <K, V> Map<K, V>?.takeOrEmpty() = this ?: emptyMap()

/**
 * Returns this [Map.Entry] if it is not null, otherwise returns an empty [Map.Entry].
 *
 * @return The non-null [Map.Entry] if it is not null, otherwise an empty [Map.Entry].
 */
fun <K, V> Map.Entry<K, V>?.takeOrEmpty() = this ?: emptyMap<K, V>().entries.first()

/**
 * Returns true if every element produced by [process] is unique.
 *
 * @param process the function used to get the value which should be unique
 * @return true if every element is unique, false otherwise
 */
inline fun <T, C> Iterable<T>.isUnique(process: (T) -> C): Boolean {
	val results = mutableSetOf<C>()
	return this.all { item -> results.add(process(item)) }
}

/**
 * Check if every element, produced by the given function, is unique.
 *
 * @param process the function used to get the value for each element
 * @return true if every element is unique, false otherwise
 */
inline fun <T, C> Array<T>.isUnique(process: (T) -> C): Boolean {
	val results = mutableSetOf<C>()
	return this.all { item -> results.add(process(item)) }
}
/**
 * This function modifies the current [MutableList] so, that the first [n] strings
 * are being merged together, with the [spliterator] and [transform] used.
 * @author Fruxz
 * @since 1.0
 */
@ExperimentalAscendApi
fun <T : MutableList<String>> T.joinFirst(n: Int, spliterator: String = ", ", transform: (String) -> String = { it }) {
	repeat(n) {
		this[1] = (transform("${removeFirstOrNull()}") + spliterator + this[1])
	}
}

/**
 * This function modifies the current [MutableList] so, that the last [n] strings
 * are being merged together, with the [spliterator] and [transform] used.
 * @author Fruxz
 * @since 1.0
 */
@ExperimentalAscendApi
fun <T : MutableList<String>> T.joinLast(n: Int, spliterator: String = ", ", transform: (String) -> String = { it }) {
	repeat(n) {
		this[lastIndex - 1] = (this[lastIndex - 1] + spliterator + transform("${removeLastOrNull()}"))
	}
}

/**
 * This function returns a modified list of the current [Iterable], with the first [n]
 * entries merged together with the [spliterator] and [transform] used.
 * This does not modify the originally used [Iterable].
 * @author Fruxz
 * @since 1.0
 */
@ExperimentalAscendApi
fun <T : Iterable<String>> T.joinedFirst(n: Int, spliterator: String = ", ", transform: (String) -> String = { it }) = modified {
	joinFirst(n, spliterator, transform)
}

/**
 * This function returns a modified list of the current [Iterable], with the last [n]
 * entries merged together with the [spliterator] and [transform] used.
 * This does not modify the originally used [Iterable].
 * @author Fruxz
 * @since 1.0
 */
@ExperimentalAscendApi
fun <T : Iterable<String>> T.joinedLast(n: Int, spliterator: String = ", ", transform: (String) -> String = { it }) = modified {
	joinLast(n, spliterator, transform)
}

/**
 * This function returns a new list of the current [Iterable], with the values
 * from the indexes between [IntRange.first] and [IntRange.last] (inclusive).
 * @author Fruxz
 * @since 1.0
 */
operator fun <T> List<T>.get(indexesRange: IntRange) = subList(indexesRange.first, indexesRange.last)

/**
 * This function returns a new list of all entries, which are at the indexes
 * specified in the [indexes].
 * @author Fruxz
 * @since 1.0
 */
operator fun <T> List<T>.get(indexes: Iterable<Int>) = indexes.map { this[it] }

/**
 * This function returns a new [SortedSet] of the current [Iterable], with the
 * indexes matching the indexes of [this].
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.toSortedSet(): SortedSet<T> {
	return when (this) {
		is SortedSet -> this
		else -> toSortedSet(compareBy { this.indexOf(it) })
	}
}

/**
 * This function returns a new [SortedSet] of the current [Array], with the
 * indexes matching the indexes of [this].
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.toSortedSet(): SortedSet<T> =
	toSortedSet(compareBy { this.indexOf(it) })