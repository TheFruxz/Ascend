package dev.fruxz.ascend.extension.container

@Deprecated(message = "Name changed to better reflect the functionality",
	replaceWith = ReplaceWith("this.replaceWith(iterable)")
)
fun <T> MutableCollection<T>.reduceTo(iterable: Iterable<T>) =
	this.replaceWith(iterable)

/**
 * Replaces the content values of [this] with the contents
 * of the [iterable].
 *
 * @param iterable The new entries of [this] collection.
 * @param T The type of elements in the collection.
 */
fun <T> MutableCollection<T>.replaceWith(iterable: Iterable<T>) {
	clear()
	addAll(iterable)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableCollection<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableSet<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableList<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}

/**
 * This function takes a nullable [C] (which is a mutable-collection-based object) and
 * returns an empty mutable list, if it is actually null, or itself, if itself was not
 * null. This function makes it easy to create new mutable collections, if there is a
 * nullable mutable collection.
 * @param C The type of the mutable-collection-based object
 * @param T The type of the elements of the mutable-collection-based object
 * @return An empty mutable list, if [C] was null, or [C] itself, if it was not null
 * @author Fruxz
 * @since 1.0
 */
fun <C : MutableCollection<T>, T> C?.orEmptyMutable() = this?.toMutableList() ?: mutableListOf()

/**
 * This function allows to get a mutable list of a collection-based object, that
 * got a new [element] added to it. This function is a little helper besides the
 * [MutableCollection.plus] function, that only returns an immutable list, instead
 * of its real type. This function returns a mutable list, that contains the
 * [element] added to the collection-based object.
 * @param C The type of the collection-based object
 * @param T The type of the elements of the collection-based object
 * @param element The element to be added to the collection-based object
 * @return A mutable list, that contains the [element] added to the collection-based object
 * @author Fruxz
 * @since 1.0
 */
infix fun <C : MutableCollection<T>, T> C.and(element: T) = apply { add(element) }

fun <C : MutableCollection<T>, T> C.addIf(element: T, check: (element: T, currentState: C) -> Boolean) {
	if (check(element, this)) add(element)
}

fun <C : MutableCollection<T>, T> C.addIfNot(element: T, check: (element: T, currentState: C) -> Boolean) =
	addIf(element) { it, c -> !check(it, c) }

fun <C : MutableCollection<T>, T> C.addIfContained(element: T) =
	addIf(element) { _, currentState -> element in currentState }

fun <C : MutableCollection<T>, T> C.addIfNotContained(element: T) =
	addIfNot(element) { _, currentState -> element in currentState }

/**
 * Adds the specified [element] to the collection and returns the same element.
 *
 * @param element the element to be added to the collection
 * @return the added element
 */
fun <C : MutableCollection<T>, T> C.addAndReturn(element: T): T =
	element.also { this.add(it) }

/**
 * Adds the specified element at the given index and returns the added element.
 *
 * @param index the index at which the element should be inserted
 * @param element the element to be added
 * @return the added element
 */
fun <C : MutableList<T>, T> C.addAndReturn(index: Int, element: T): T =
	element.also { this.add(index, it) }