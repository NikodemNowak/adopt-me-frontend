package com.nikodem.adoptme.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikodem.adoptme.db.dao.UserDao
import com.nikodem.adoptme.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
