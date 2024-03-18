package dev.fruxz.ascend.extension.security

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * The HashUtils object provides functions to hash [ByteArray]s
 * and [String]s with the given algorithm.
 * @author Fruxz
 * @since 2023.1
 */
object HashUtils {

    /**
     * Uses the [hash] function to hash the given [ByteArray] with the md5 algorithm.
     */
    @WeakCryptographicAlgorithm("MD5 is a weak cryptographic algorithm and should not be used.")
    fun md5(input: String) = hash("MD5", input)

    /**
     * Uses the [hash] function to hash the given [ByteArray] with the sha512 algorithm.
     */
    fun sha512(input: String) = hash("SHA-512", input)

    /**
     * Uses the [hash] function to hash the given [ByteArray] with the sha256 algorithm.
     */
    fun sha256(input: String) = hash("SHA-256", input)

    /**
     * Uses the [hash] function to hash the given [ByteArray] with the sha1 algorithm.
     */
    fun sha1(input: String) = hash("SHA-1", input)

    /**
     * Hashes the given [ByteArray] with the given [algorithm].
     */
    @Throws(NoSuchAlgorithmException::class)
    fun hash(algorithm: String, input: ByteArray): ByteArray =
        MessageDigest
            .getInstance(algorithm)
            .digest(input)

    /**
     * Hashes the given [String] with the given [algorithm].
     */
    @Throws(NoSuchAlgorithmException::class)
    fun hash(algorithm: String, input: String) =
        hash(algorithm, input.encodeToByteArray())
            .joinToString("") { byte -> "%02x".format(byte) }

}

/**
 * This enum class defines some supported hash types.
 * @author Fruxz
 * @since 2023.1
 */
enum class HashType {

    @WeakCryptographicAlgorithm("MD5 is a weak cryptographic algorithm and should not be used.")
    MD5,

    SHA1,
    SHA256,
    SHA512;

    val hash: (String) -> String
        get() = @OptIn(WeakCryptographicAlgorithm::class) when (this) {
            MD5 -> HashUtils::md5
            SHA1 -> HashUtils::sha1
            SHA256 -> HashUtils::sha256
            SHA512 -> HashUtils::sha512
        }

    /**
     * Hashes the given [String] with this [HashType]s [hash] process.
     */
    infix fun on(input: String) = hash(input)

}