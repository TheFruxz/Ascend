import dev.fruxz.ascend.extension.or
import dev.fruxz.ascend.extension.switch
import kotlin.test.Test

class BooleanTest {

    @Test
    fun `Check booleans with switch functions and DSL`() {
        val match = true
        val mismatch = false

        val check1 = match switch "match" or "mismatch"
        assert(check1 == "match") { "Check1 must be 'match', but was '$check1' instead!" }

        val check2 = mismatch switch "match" or "mismatch"
        assert(check2 == "mismatch") { "Check2 must be 'mismatch', but was '$check2' instead!" }

        val check3 = match.switch("match", "mismatch")
        assert(check3 == "match") { "Check3 must be 'match', but was '$check3' instead!" }

        val check4 = mismatch.switch("match", "mismatch")
        assert(check4 == "mismatch") { "Check4 must be 'mismatch', but was '$check4' instead!" }

    }

}