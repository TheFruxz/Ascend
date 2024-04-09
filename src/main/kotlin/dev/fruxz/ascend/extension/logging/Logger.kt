package dev.fruxz.ascend.extension.logging

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import org.slf4j.Logger as Slf4jLogger
import java.util.logging.Logger as JavaUtilLogger

/**
 * Creates a logger with the given name.
 * @param name the name of the logger
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 2023.1
 */
fun getLogger(name: String): JavaUtilLogger = JavaUtilLogger.getLogger(name)

/**
 * Creates a logger with the given name and resource bundle.
 * @param name the name of the logger
 * @param resourceBundleName the resource bundle
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 2023.1
 */
fun getLogger(name: String, resourceBundleName: String): JavaUtilLogger = JavaUtilLogger.getLogger(name, resourceBundleName)

/**
 * Creates a logger from the name of given class.
 * @param clazz the class
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 2023.1
 */
fun getLogger(clazz: KClass<*>) =
	getLogger(clazz.simpleName ?: "anonymous")

/**
 * Creates a logger from the [JavaUtilLogger] using the class of the given object.
 * @return the logger
 * @see getLogger
 * @author Fruxz
 * @since 2023.1
 */
fun <T : Any> T.getItsLogger() = getLogger(this::class)

/**
 * Creates a logger from the [clazz] using the [LoggerFactory].
 * @param clazz the class
 * @return the logger
 * @see LoggerFactory.getLogger
 * @see Slf4jLogger
 * @author Fruxz
 * @since 2023.5.3
 */
fun <T : Any> getFactoryLogger(clazz: KClass<T>): Slf4jLogger =
	LoggerFactory.getLogger(clazz.java)

/**
 * Creates a logger from the class of [this] using the [LoggerFactory].
 * @return the logger
 * @see LoggerFactory.getLogger
 * @see Slf4jLogger
 * @author Fruxz
 * @since 2023.5.3
 */
fun <T : Any> T.getThisFactoryLogger(): Slf4jLogger =
	getFactoryLogger(this::class)