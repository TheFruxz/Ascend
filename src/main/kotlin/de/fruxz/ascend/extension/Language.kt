package de.fruxz.ascend.extension

import de.fruxz.ascend.annotation.LanguageFeature

/**
 * This function uses the current [this] element, puts it in the `it` perspective,
 * uses the [it], puts it in the `this` perspective and uses both of them in the
 * [block], which returns a result of [O].
 * In short and simple: uses the [it] as the current context, by also keeping
 * the current one, but as a own parameter
 * @author Fruxz
 * @since 1.0
 */
@LanguageFeature
fun <T, I, O> T.alsoWith(it: I, block: I.(T) -> O): O = it.block(this)