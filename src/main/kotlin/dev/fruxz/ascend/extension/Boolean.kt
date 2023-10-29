package dev.fruxz.ascend.extension

/**
 * This functions returns [match] if [this] is true, otherwise [mismatch].
 * @param match the result if [this] is true
 * @param mismatch the result if [this] is false
 * @return [match] if [this] is true, otherwise [mismatch]
 * @author Fruxz
 * @since 2023.1
 */
fun <T> Boolean.switch(match: T, mismatch: T) = when {
    this -> match
    else -> mismatch
}

/**
 * This value class defines the boolean and true-output of a switch function.
 * @author Fruxz
 * @since 2023.5
 */
@JvmInline
value class BooleanSwitchSide<T>(val base: Pair<Boolean, T>)

/**
 * Defines the output if the boolean is true.
 * Returns a [BooleanSwitchSide] to define the output if the boolean is false.
 * @author Fruxz
 * @since 2023.5
 */
infix fun <T> Boolean.switch(match: T): BooleanSwitchSide<T> = BooleanSwitchSide(this to match)

/**
 * Defines the output if the boolean is false.
 * Also, directly returns the result.
 * @author Fruxz
 * @since 2023.5
 */
infix fun <T> BooleanSwitchSide<T>.or(mismatch: T) = when {
    this.base.first -> this.base.second
    else -> mismatch
}