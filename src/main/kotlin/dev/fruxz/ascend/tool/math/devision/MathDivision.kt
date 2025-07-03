package dev.fruxz.ascend.tool.math.devision

object MathDivision {

    /**
     * Returns the least common multiple of the given numbers.
     *
     * @param numbers a collection of integers
     * @return the least common multiple of the numbers
     * @author Fruxz
     * @since 2025.7
     */
    fun greatestCommonDivisor(numbers: Collection<Int>): Int {
        require(numbers.isNotEmpty()) { "List must not be empty" }
        var result = numbers.first()

        for (i in 1 until numbers.size) {
            var num1 = result
            var num2 = numbers.elementAt(i)
            while (num2 != 0) {
                val temp = num2
                num2 = num1 % num2
                num1 = temp
            }
            result = num1
        }
        return result
    }

}