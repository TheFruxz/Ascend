package de.fruxz.ascend.extension.logging

import kotlin.reflect.KClass
import java.util.logging.Logger as JavaUtilLogger

/**
 * Creates a logger with the given name.
 * @param name the name of the logger
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(name: String): JavaUtilLogger = JavaUtilLogger.getLogger(name)

/**
 * Creates a logger with the given name and resource bundle.
 * @param name the name of the logger
 * @param resourceBundleName the resource bundle
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(name: String, resourceBundleName: String): JavaUtilLogger = JavaUtilLogger.getLogger(name, resourceBundleName)

/**
 * Creates a logger from the name of given class.
 * @param clazz the class
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(clazz: KClass<*>) =
	getLogger(clazz.simpleName ?: "generic")

/**
 * Creates a logger from the [JavaUtilLogger] using the class of the given object.
 * @return the logger
 * @see getLogger
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any> T.getItsLogger() = getLogger(this::class)
