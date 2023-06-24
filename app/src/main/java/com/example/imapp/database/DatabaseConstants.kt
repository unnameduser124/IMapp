package com.example.imapp.database

object DatabaseConstants{
    const val DATABASE_NAME = "UserDatabase"
    const val DATABASE_VERSION = 1

    const val CREATE_USER_TABLE_QUERY = "CREATE TABLE ${UserTable.TABLE_NAME} (" +
            "${UserTable.ID_COLUMN} TEXT PRIMARY KEY, " +
            "${UserTable.USERNAME_COLUMN} TEXT NOT NULL)"

    object UserTable{
        const val TABLE_NAME = "UserTable"
        const val ID_COLUMN = "UserID"
        const val USERNAME_COLUMN = "Username"
    }
}