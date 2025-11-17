package dev.fruxz.ascend.extension.property

import dev.fruxz.ascend.tool.time.calendar.Calendar
import kotlin.reflect.KProperty
import kotlin.time.Duration

/**
 * Cached property delegate that regenerates the value on access, after a specified [duration].
 * @author Fruxz
 * @since 2025.11
 */
data class CachedProperty<T>(
    val duration: Duration,
    val builder: () -> T,
) {
    var state: T? = null
    var updated: Calendar? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (updated == null || updated!!.durationToNow() > duration) {
            state = builder()
            updated = Calendar.now()
        }
        return state!!
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state = value
        updated = Calendar.now()
    }

}

/**
 * Generates the property value on first call, or if [duration] has passed since last update.
 * The value is cached internally between calls.
 * @author Fruxz
 * @since 2025.11
 */
fun <T> cached(
    duration: Duration,
    builder: () -> T,
) = CachedProperty(duration = duration) { builder() }