package com.example.imapp

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.security.SecureRandom

class PasswordHashTests {

    @Test
    fun testHashPassword() {
        val random = SecureRandom()
        val salt = MainUser.getInstance().generateSalt()
        val password = "password"

        val hashPassword1 = MainUser.getInstance().hashPassword(password, salt)
        val hashPassword2 = MainUser.getInstance().hashPassword(password, salt)
        assertEquals(hashPassword1, hashPassword2)
    }
}