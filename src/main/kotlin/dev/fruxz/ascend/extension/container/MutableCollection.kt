package dev.fruxz.ascend.extension.container

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
 * If contained, remove; or if not contained, add the element.
 * @param element The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @return if it has been added [MutableCollection.add] gets returned, or if it has been removed [MutableCollection.remove] gets returned
 * @author Fruxz
 * @since 2023.1
 */
fun <T> MutableCollection<T>.toggle(element: T, addToContainer: Boolean = !contains(element)): Boolean = when {
	addToContainer -> add(element)
	else -> remove(element)
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
 * @since 2023.1
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
 * @since 2023.1
 */
infix fun <C : MutableCollection<T>, T> C.and(element: T) = apply { add(element) }

/**
 * Adds the specified [element] to this mutable collection if the [check] function
 * returns true when called with the [element] and the current state of this collection.
 *
 * @param element The element to be added.
 * @param check A function that takes the [element] and the current state of this collection
 * and returns true if the [element] should be added, false otherwise.
 */
fun <C : MutableCollection<T>, T> C.addIf(element: T, check: (element: T, currentState: C) -> Boolean) {
	if (check(element, this)) add(element)
}

/**
 * Adds the specified [element] to the collection if it satisfies the given [check].
 *
 * @param element The element to be added to the collection.
 * @param check A function that takes an element and the current state of the collection as parameters and returns a boolean
 * indicating whether the element should be added based on the check.
 * @return `true` if the element was added, `false` otherwise.
 */
fun <C : MutableCollection<T>, T> C.addIfNot(element: T, check: (element: T, currentState: C) -> Boolean) =
	addIf(element) { it, c -> !check(it, c) }

/**
 * Adds the specified [element] to the collection if it is already contained in the collection.
 *
 * This method checks whether the [element] is present in the collection before adding it. If the [element] is already
 * contained, it will not be added again. The check for containment is done using the `in` operator.
 *
 * @param element The element to be added to the collection if it is already contained.
 * @return `true` if the [element] was added to the collection, `false` otherwise.
 *
 * @param C The type of the mutable collection.
 * @param T The type of the elements in the collection.
 *
 * @see MutableCollection
 */
fun <C : MutableCollection<T>, T> C.addIfContained(element: T) =
	addIf(element) { _, currentState -> element in currentState }

/**
 * Adds the specified element to the collection if it is not already contained in it.
 *
 * @param element the element to be added to the collection.
 * @return `true` if the element was added to the collection, `false` if the element is already contained in the collection.
 */
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