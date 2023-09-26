import dev.fruxz.ascend.extension.container.joinFirst
import dev.fruxz.ascend.extension.container.joinLast
import kotlin.test.Test

class CollectionTest {

    @Test
    fun `Check joinFirst() on mutable collections`() {
        val list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h")

        list.joinFirst(n = 2, spliterator = "-")

        assert(list == mutableListOf("a-b-c", "d", "e", "f", "g", "h")) { "List is not equal to expected list!" }
    }

    @Test
    fun `Check joinLast() on mutable collections`() {
        val list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h")

        list.joinLast(n = 2, spliterator = "-")

        assert(list == mutableListOf("a", "b", "c", "d", "e", "f-g-h")) { "List is not equal to expected list!" }
    }

}