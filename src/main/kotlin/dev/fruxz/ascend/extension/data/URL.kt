package dev.fruxz.ascend.extension.data

import org.intellij.lang.annotations.Language
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

/**
 * Converts a given URL string to a `URL` object.
 *
 * @param url The string representing the URL.
 * @return A `URL` object representing the specified URL string.
 * @throws URISyntaxException If the URL string violates syntax rules.
 * @throws MalformedURLException If a protocol handler for the URL could not be found, or if some other error occurred while constructing the
 */
@Throws(URISyntaxException::class, MalformedURLException::class)
fun url(@Language("url") url: String): URL = uri(url).toURL() // create URL(...) is deprecated since Java 20

/**
 * Creates a [URI] object from the given URI string.
 *
 * @param uri The URI string to create the [URI] object from.
 * @return A [URI] object representing the given URI string.
 * @throws URISyntaxException If the given URI string violates syntax rules.
 */
@Throws(URISyntaxException::class)
fun uri(@Language("uri") uri: String) = URI(uri)