package dev.fruxz.ascend.extension.data

import org.intellij.lang.annotations.Language
import java.net.URI
import java.net.URL

fun url(@Language("url") url: String) = URL(url)

fun uri(@Language("uri") uri: String) = URI(uri)