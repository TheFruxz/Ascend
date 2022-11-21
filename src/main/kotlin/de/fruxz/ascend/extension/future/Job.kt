package de.fruxz.ascend.extension.future

import kotlinx.coroutines.Job

/**
 * This function is a wrapper for [Job.join]!
 */
suspend fun Job.await() = this.join()