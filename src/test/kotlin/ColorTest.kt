import dev.fruxz.ascend.extension.Color
import dev.fruxz.ascend.extension.hexString
import kotlin.test.Test

class ColorTest {

    @Test
    fun `Check the hex-color conversion`() {
        val color = Color("#F53")
        assert(color.hexString == "#ff5533") { "Color must be '#ff5533', but was '" + color.hexString + "' instead!" }
    }

}