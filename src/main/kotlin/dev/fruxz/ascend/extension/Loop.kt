package dev.fruxz.ascend.extension

import dev.fruxz.ascend.annotation.LanguageFeature

/**
 * A representation of a custom loop.
 * @author Fruxz
 * @since 2023.5
 */
data class Loop(var running: Boolean = true) {
    @LanguageFeature fun scheduleBreak() { running = false }
}

/**
 * This function quickly creates an infinite repeating loop.
 * This loop can be stopped inline, but the loop.stop() function
 * does not stop the loop immediately, but only after the current iteration.
 * This means to immediately break a loop, return the lambda.
 * @author Fruxz
 * @since 2023.5
 */
@LanguageFeature
inline fun loop(process: (Loop) -> Unit) {
    while (true) {
        val loop = Loop()
        process(loop)
        if (!loop.running) break
    }
}