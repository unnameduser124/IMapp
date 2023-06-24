package com.example.imapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class UserDatabase(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseConstants.DATABASE_NAME,
    null,
    DatabaseConstants.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseConstants.CREATE_USER_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}