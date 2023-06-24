package com.example.imapp

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class MainUser(
    val userID: String,
    var username: String,
    var password: String,
    val conversationIDs: List<Long>
) {
    private val random = SecureRandom()

    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun hashPassword(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, 1000, 256)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    companion object {
        private val instance: MainUser? = null
        fun getInstance(): MainUser {
            return instance ?: MainUser(
                "",
                "",
                "",
                listOf()
            )
        }
    }
}
