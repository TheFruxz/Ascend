import dev.fruxz.ascend.extension.container.joinArgumentChunks
import kotlin.test.Test

class ArgumentChunksTest {

    @Test
    fun `Test String to Argument Chunks`() {
        val input = "This is an \"Input string with\" some \"working and not working chunks!"

        assert(input.joinArgumentChunks() == listOf("This", "is", "an", "Input string with", "some", "\"working", "and", "not", "working", "chunks!"))
        
    }

}