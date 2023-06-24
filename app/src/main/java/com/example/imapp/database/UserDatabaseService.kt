package com.example.imapp.database

import android.content.ContentValues
import android.content.Context
import com.example.imapp.MainUser

class UserDatabaseService(context: Context): UserDatabase(context){

    fun addUser(user: MainUser){
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put(DatabaseConstants.UserTable.ID_COLUMN, user.userID)
            put(DatabaseConstants.UserTable.USERNAME_COLUMN, user.username)
        }
        db.insert(DatabaseConstants.UserTable.TABLE_NAME, null, values)
    }

    fun getUser(){
        if(!checkIfUserIsSavedInTheDatabase()){
            return
        }
        val db = this.readableDatabase
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
                MainUser.userID = userID
                MainUser.username = username
                return
            }
        }
    }

    fun deleteUser(){
        val db = this.writableDatabase
        db.delete(DatabaseConstants.UserTable.TABLE_NAME, null, null)
    }

    fun updateUser(user: MainUser){
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
            put(DatabaseConstants.UserTable.ID_COLUMN, user.userID)
            put(DatabaseConstants.UserTable.USERNAME_COLUMN, user.username)
        }
        db.update(
            DatabaseConstants.UserTable.TABLE_NAME,
            contentValues,
            null,
            null
        )
    }

    private fun checkIfUserIsSavedInTheDatabase(): Boolean{
        val db = this.readableDatabase
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
            if(moveToFirst()){
                return true
            }
        }
        return false
    }
}