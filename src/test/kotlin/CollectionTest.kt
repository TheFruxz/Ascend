import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.extension.container.joinFirst
import dev.fruxz.ascend.extension.container.joinLast
import kotlin.test.Test

class CollectionTest {

    @Test
    @OptIn(ExperimentalAscendApi::class)
    fun joinFirst() {
        val list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h")

        list.joinFirst(2, "-")

        println(list)

        assert(list == mutableListOf("a-b", "c", "d", "e", "f", "g", "h")) { "List is not equal to expected list!" }
    }

    @Test
    @OptIn(ExperimentalAscendApi::class)
    fun joinLast() {
        val list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h")

        list.joinLast(2, "-")

        println(list)

        assert(list == mutableListOf("a", "b", "c", "d", "e", "f", "g-h")) { "List is not equal to expected list!" }
    }

}