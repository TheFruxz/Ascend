package dev.fruxz.ascend.extension.objects

/**
 * Using .apply { suspendedProcess.invoke() } is possible, but .apply(suspendedProcess) is not possible, this adds this functionality.
 * @author Fruxz
 * @since 2023.2
 */
suspend fun <T> T.applySuspend(process: suspend T.() -> Unit) = apply { process.invoke(this) }