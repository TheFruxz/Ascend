package dev.fruxz.ascend.extension.data

import java.nio.charset.Charset

/**
 * Returns the text of the web page at the given [url].
 * @param url the url of the web page, which will be downloaded
 * @return the text of the web page
 * @author Fruxz
 * @since 2023.1
 */
@Throws(java.net.URISyntaxException::class, java.net.MalformedURLException::class)
fun getWebText(url: String, charset: Charset = Charsets.UTF_8) = url(url).readText(charset)

/**
 * Returns the content of the web page at the given [url].
 * @param url the url of the web page, which will be downloaded
 * @return the content of the web page as [ByteArray]
 * @author Fruxz
 * @since 2023.1
 */
@Throws(java.net.URISyntaxException::class, java.net.MalformedURLException::class)
fun readWebBytes(url: String) = url(url).readBytes()