package com.example.imapp

import org.mindrot.jbcrypt.BCrypt
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

    fun generateSalt(): String {
        val saltRounds = 12 // Number of salt rounds (cost factor)
        val secureRandom = SecureRandom()
        val salt = ByteArray(16)
        secureRandom.nextBytes(salt)
        return BCrypt.gensalt(saltRounds, secureRandom) // Generate salt using bcrypt
    }

    fun hashPassword(password: String, salt: String): String {
        return BCrypt.hashpw(password, salt)
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
