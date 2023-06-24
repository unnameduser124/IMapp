package com.example.imapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.imapp.database.DatabaseConstants
import com.example.imapp.database.UserDatabaseService
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserDatabaseTests {

    private lateinit var dbService: UserDatabaseService

    @Before
    fun setUp(){
        dbService = UserDatabaseService(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown(){
        ApplicationProvider.getApplicationContext<Context>().deleteDatabase(DatabaseConstants.DATABASE_NAME)
    }

    @Test
    fun addUserToDatabaseTest(){
        val user = MainUser.apply {
            userID = "123"
            username = "TestUser"
        }
        dbService.addUser(user)

        val db = dbService.readableDatabase
        val projection = arrayOf(
            DatabaseConstants.UserTable.ID_COLUMN,
            DatabaseConstants.UserTable.USERNAME_COLUMN
        )
        val cursor = db.query(
            DatabaseConstants.UserTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor){
            while(moveToNext()){
                val userID = getString(getColumnIndexOrThrow(DatabaseConstants.UserTable.ID_COLUMN))
                val username = getString(getColumnIndexOrThrow(DatabaseConstants.UserTable.USERNAME_COLUMN))
                assert(userID == user.userID)
                assert(username == user.username)
            }
        }
    }

    @Test
    fun getUserFromdatabaseTest(){
        val user = MainUser.apply {
            userID = "123"
            username = "TestUser"
        }
        dbService.addUser(user)
        dbService.getUser()
        assert("123" == MainUser.userID)
        assert("TestUser" == MainUser.username)
    }

    @Test
    fun deleteUserFromDatabaseTest(){
        val user = MainUser.apply {
            userID = "123"
            username = "TestUser"
        }
        dbService.addUser(user)
        dbService.deleteUser()
        val db = dbService.readableDatabase
        val projection = arrayOf(
            DatabaseConstants.UserTable.ID_COLUMN,
            DatabaseConstants.UserTable.USERNAME_COLUMN
        )
        val cursor = db.query(
            DatabaseConstants.UserTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor){
            assert(count == 0)
        }
    }

    @Test
    fun updateUserTest(){
        val user = MainUser.apply {
            userID = "123"
            username = "TestUser"
        }
        dbService.addUser(user)
        user.userID = "321"
        user.username = "UserTest"
        dbService.updateUser(user)
        dbService.getUser()
        assert("321" == MainUser.userID)
        assert("UserTest" == MainUser.username)
    }

}