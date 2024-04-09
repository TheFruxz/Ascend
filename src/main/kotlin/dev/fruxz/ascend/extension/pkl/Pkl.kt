package dev.fruxz.ascend.extension.pkl

import org.pkl.config.java.ConfigEvaluator
import org.pkl.config.java.ConfigEvaluatorBuilder
import org.pkl.config.kotlin.forKotlin

/**
 * This lazily computed value returns a Kotlin instance of
 * the apple/pkl ConfigEvaluator preconfigured instance.
 * @see ConfigEvaluator
 * @see ConfigEvaluatorBuilder
 * @see forKotlin
 * @author Fruxz
 * @since 2024.2
 */
val pkl by lazy { ConfigEvaluator.preconfigured().forKotlin() }

/**
 * This function builds a new Kotlin instance of the apple/pkl ConfigEvaluator
 * using the preconfigured() function and applies the [builder] function to it.
 * @see ConfigEvaluator
 * @see ConfigEvaluatorBuilder
 * @see forKotlin
 * @param builder the function to apply to the preconfigured ConfigEvaluatorBuilder
 * @return the new Kotlin instance of the ConfigEvaluator
 * @author Fruxz
 * @since 2024.2
 */
fun buildPkl(builder: ConfigEvaluatorBuilder.() -> Unit) = ConfigEvaluatorBuilder.preconfigured().apply(builder).forKotlin()

/**
 * This function builds a new Kotlin instance of the apple/pkl ConfigEvaluator
 * using the unconfigured() function and applies the [builder] function to it.
 * @see ConfigEvaluator
 * @see ConfigEvaluatorBuilder
 * @see forKotlin
 * @param builder the function to apply to the unconfigured ConfigEvaluatorBuilder
 * @return the new Kotlin instance of the ConfigEvaluator
 * @author Fruxz
 * @since 2024.2
 */
fun buildUnconfiguredPkl(builder: ConfigEvaluatorBuilder.() -> Unit) = ConfigEvaluatorBuilder.unconfigured().apply(builder).forKotlin()