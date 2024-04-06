package dev.fruxz.ascend.extension.security

import dev.fruxz.ascend.annotation.WeakCryptographicAlgorithm

/**
 * Computes the MD5 hash value of the given string.
 *
 * @param input The input string for which the MD5 hash value is to be calculated.
 * @return The MD5 hash value of the input string as a hexadecimal string.
 */
@WeakCryptographicAlgorithm("MD5 is a weak cryptographic algorithm and should not be used.")
fun md5(input: String) = HashUtils.md5(input)

/**
 * Computes the SHA-512 hash value of the given input string.
 *
 * @param input the string to compute the hash value for
 * @return the SHA-512 hash value represented as a string
 */
fun sha512(input: String) = HashUtils.sha512(input)

/**
 * Computes the SHA-256 hash value of the given input string.
 *
 * @param input The input string to compute the hash value for.
 * @return The SHA-256 hash value as a string.
 */
fun sha256(input: String) = HashUtils.sha256(input)

/**
 * Computes the SHA-1 hash value of the given input string.
 *
 * @param input The input string for which the SHA-1 hash value needs to be calculated.
 * @return The SHA-1 hash value of the input string.
 */
fun sha1(input: String) = HashUtils.sha1(input)

/**
 * This function hashes a given string with the specified hash algorithm.
 *
 * @param type The hash algorithm to use.
 * @return The hashed representation of the string.
 */
infix fun String.hashed(type: HashType) = type.hash(this)