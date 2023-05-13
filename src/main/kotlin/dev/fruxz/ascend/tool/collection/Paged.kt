package dev.fruxz.ascend.tool.collection

import dev.fruxz.ascend.extension.container.subList
import dev.fruxz.ascend.extension.container.subListOrEmpty
import dev.fruxz.ascend.extension.math.ceilToInt

/**
 * This data class splits the content of the [content] list into the page-parts.
 * Each page contains [size] amount of items, but the last page can have fewer
 * contents, if they cannot fill the last page.
 * Technically, this class itself does not instantly split the [content] into its
 * pages. It only provides the content of the pages, when requested.
 * So we use [List.subList] instead of [List.chunked].
 * @see List.subList
 * @author Fruxz
 * @since 1.0
 */
data class Paged<T>(
    val size: Int,
    val content: Iterable<T>,
) : Iterable<List<T>>, Comparable<Paged<T>>, Cloneable {

    /**
     * This function returns the page of the [page] index as an [PageIterable].
     * @author Fruxz
     * @since 1.0
     */
    fun getPage(page: Int) = PageIterable(
        page = page,
        content = (size * (page + 1)).let { end ->
            val from = size * page
            val to = end.coerceAtMost(content.count())

            content.toList().subListOrEmpty(from..to)

        }
    )

    /**
     * This function returns the pages of the [pages] indexes as a list of [PageIterable]s.
     * @see getPage
     * @author Fruxz
     * @since 1.0
     */
    fun getPages(pages: Iterable<Int>) = pages.map { page -> getPage(page) }

    /**
     * This computational value represents the amount of pages, that this [Paged] object has.
     * @author Fruxz
     * @since 1.0
     */
    val pages by lazy { ceilToInt(content.count().toDouble() / size) }

    override fun iterator(): ListIterator<List<T>> = content
        .chunked(size)
        .listIterator()

    override fun compareTo(other: Paged<T>) = content.count() - other.content.count()

    /**
     * This class represents a part of an iterable, which represents itself a page (for e.g. page 3 of 10).
     * This data class represents a page of an [Paged] object, of type [T]
     * @param page The index of the page
     * @param content The content of this exact page
     * @see Paged
     * @author Fruxz
     * @since 1.0
     */
    data class PageIterable<T>(
        val page: Int,
        val content: List<T>,
    ) : Iterable<T>, Comparable<PageIterable<T>>, Cloneable {

        /**
         * This property represents the amount of items inside this page.
         * @see List.size
         * @author Fruxz
         * @since 1.0
         */
        val size: Int = content.size

        override fun iterator() = content.iterator()

        override fun compareTo(other: PageIterable<T>) = page - other.page

    }

}