package com.example.imapp

import org.mindrot.jbcrypt.BCrypt
import java.security.SecureRandom


object MainUser
{

    var userID: String = ""
    var username: String = ""

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

}
