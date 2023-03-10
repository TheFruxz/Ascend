package de.fruxz.ascend.extension.container

import de.fruxz.ascend.annotation.ExperimentalAscendApi
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.tool.collection.Paged
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * # `C.toArrayList()`
 * ## Info
 * This function creates a new [ArrayList]<[T]> object, which
 * contains the elements of the Collection<[T]> [C] (**this**-object)
 *
 * ## Use
 * This function can be easily used to get a set or list to an ArrayList,
 * but you can also use `ArrayList(yourList)`, that is the same, what is also
 * used in this function!
 *
 * ## Base
 * This function is globally available through the whole Ascend API and beyond!
 *
 * This function creates a new [ArrayList] object of containing-type [T], obtained
 * from the Collection [C], which has also the containing-type [T]. This [toArrayList]
 * function is attached as an extension function to the [C] object, which is based
 * on all [Collection] type [T]!
 *
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-5 (preview)
 * @param T the inner containing data type
 * @param C the actual base collection, which will be transformed
 */
fun <T, C : Collection<T>> C.toArrayList(): ArrayList<T> = ArrayList(this)

/**
 * # `Array<out T>.toArrayList()`
 * ## Info
 * This function creates a new [ArrayList]<[T]> object, which
 * contains the elements of the Array<out [T]> (**this**-object)
 *
 * ## Use
 * This function can be easily used tzo get an array to an ArrayList,
 * but you can also use `ArrayList(yourArray.toList())`, that is the same, what is also
 * used in this function!
 *
 * ## Base
 * This function is globally available through the whole Ascend API and beyond!
 *
 * This function creates a new [ArrayList] object of containing-type [T], obtained from the Array<out [T]>.
 * This [toArrayList] function is attached as an extension function to the Array<out [T]> object.
 *
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-5 (preview)
 * @param T the inner containing data type of both, input [Array] and output [ArrayList]
 */
fun <T> Array<out T>.toArrayList(): ArrayList<out T> = toList().toArrayList()

fun <T, C : Collection<T>> C.stackRandom(times: Int, random: Random = Random): String = buildString {
	repeat(times) { append(this@stackRandom.random(random)) }
}

/**
 * Stacks the element randomized [times] times
 * @param times the amount of repeats
 * @return the randomized string
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<out T>.stackRandom(times: Int, random: Random = Random): String = buildString {
	repeat(times) { append(this@stackRandom.random(random)) }
}

/**
 * Stacks the element randomized [times] times
 * @param times the amount of repeats
 * @return the randomized string
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : Iterable<T>> C.stackUniqueRandom(times: Int, random: Random = Random): String =
	shuffled(random).take(times).joinToString(separator = "")

/**
 * Stacks the element randomized [times] times
 * @param times the amount of repeats
 * @return the randomized string
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<out T>.stackUniqueRandom(times: Int, random: Random = Random): String =
	toList().stackUniqueRandom(times, random)

/**
 * This function creates a list of [T] objects, created
 * by each entries [generator] function.
 * @param T the inner containing data type
 * @param size the size of the list
 * @param generator the function, which will be used to generate an entry
 * @return the list of [T] objects
 * @author Fruxz
 * @since 1.0
 */
inline fun <T> listOf(size: Int, generator: (Int) -> T): List<T> = List(size) { generator(it) }

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
fun <T, C : Iterable<T>> C.get(index: Int, overflow: Boolean = false): T {
	return if (index in 0 until count()) elementAt(index) else if (overflow) elementAt(index % count()) else throw NoSuchElementException(
		"Index $index is not inside lists 0..${count() - 1} content and overflow is disabled!"
	)
}

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
 * are in a simulated page, which is created by the [pageSize]
 * and the [paged] number.
 * If the requested page is out of range, it will return the last non-empty page.
 * @param pageSize the size of each individual page
 * @return the list of [T] objects contained in the page
 * @author Fruxz
 * @since 1.0
 */
fun <T, C : List<T>> C.paged(pageSize: Int): Paged<T> =
	Paged(pageSize, this)

/**
 * This function returns a small list of [T] objects, that
 * are in a simulated page, which is created by the [pageSize]
 * and the [page] number.
 * If the requested page is out of range, it will return the last non-empty page.
 * @param page the page, where the list should be
 * @param pageSize the size of each individual page
 * @return the list of [T] objects contained in the page
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.paged(pageSize: Int): Paged<T> =
	toList().paged(pageSize)

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
 * @param process defines the value, with whic
 * object the differentiation is made
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
 * @param process defines the value, with whic
 * object the differentiation is made
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
 * This function creates a list of fragments of the origin list.
 * Example:
 * - (1, 2, 3).fragmented(2, true) -> [(1, 2), (3)]
 * - (1, 2, 3).fragmented(2, false) -> [(1, 2)]
 * @param fragments the number of fragments
 * @param keepOverflow if the last incomplete fragment should be kept
 * @return the list of fragments
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("Switch, using the chunked(...) function instead")
fun <T, C : Iterable<T>> C.fragmented(fragments: Int = 2, keepOverflow: Boolean = true): List<List<T>> =
	(this.count().toDouble() / fragments).ceilToInt().let { size ->
		chunked(size).dropLastWhile { !keepOverflow && it.size < size }
	}

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

inline fun <C : Iterable<I>, I, O> C.flatMapNotNull(builder: (I) -> Iterable<O?>): List<O & Any> = buildList {
	this@flatMapNotNull.forEach { t ->
		builder.invoke(t).forEachNotNull { o ->
			add(o)
		}
	}
}

fun <T : Iterable<O>, O> Iterable<T>.flattenNotNull() = flatMapNotNull { it }

inline fun <I, O> Array<I>.flatMapNotNull(builder: (I) -> Iterable<O?>): List<O & Any> = buildList {
	this@flatMapNotNull.forEach { t ->
		builder.invoke(t).forEachNotNull { o ->
			add(o)
		}
	}
}

fun <T : Iterable<O>, O> Array<T>.flattenNotNull() = flatMapNotNull { it }

fun <T> List<T>?.takeOrEmpty() = this ?: emptyList()

fun <T> Set<T>?.takeOrEmpty() = this ?: emptySet()

inline fun <reified T> Array<T>?.takeOrEmpty() = this ?: emptyArray()

fun <K, V> Map<K, V>?.takeOrEmpty() = this ?: emptyMap()

fun <K, V> Map.Entry<K, V>?.takeOrEmpty() = this ?: emptyMap<K, V>().entries.first()

/**
 * This function returns, if every element, produced by [process] is unique.
 * @param process the function, which is used to get the value, which should be unique
 * @return true, if every element is unique, false otherwise
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C> Iterable<T>.isUnique(process: (T) -> C) = groupBy { process(it) }.all { it.value.size == 1 }

/**
 * This function returns, if every element, produced by [process] is unique.
 * @param process the function, which is used to get the value, which should be unique
 * @return true, if every element is unique, false otherwise
 * @author Fruxz
 * @since 1.0
 */
inline fun <T, C> Array<T>.isUnique(process: (T) -> C) = groupBy { process(it) }.all { it.value.size == 1 }

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