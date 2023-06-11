package dev.fruxz.ascend.extension

/**
 * This functions returns [match] if [this] is true, otherwise [mismatch].
 * @param match the result if [this] is true
 * @param mismatch the result if [this] is false
 * @return [match] if [this] is true, otherwise [mismatch]
 * @author Fruxz
 * @since 1.0
 */
fun <T> Boolean.switch(match: T, mismatch: T) = if (this) match else mismatch