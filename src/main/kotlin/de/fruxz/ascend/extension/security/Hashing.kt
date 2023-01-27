package de.fruxz.ascend.extension.security

fun md5(input: String) = HashUtils.md5(input)

fun sha512(input: String) = HashUtils.sha512(input)

fun sha256(input: String) = HashUtils.sha256(input)

fun sha1(input: String) = HashUtils.sha1(input)

/**
 * This function hashes this [String] with the given [HashType].
 * @author Fruxz
 * @since 1.0
 */
infix fun String.hashed(type: HashType) = type.hash(this)