package com.nikodem.adoptme.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikodem.adoptme.db.dao.AnimalDao
import com.nikodem.adoptme.db.dao.QuestionAnswersDao
import com.nikodem.adoptme.db.dao.RemoteKeyDao
import com.nikodem.adoptme.db.dao.UserDao
import com.nikodem.adoptme.db.entity.AnimalDB
import com.nikodem.adoptme.db.entity.QuestionAnswersDB
import com.nikodem.adoptme.db.entity.User

@Database(
    entities = [
        User::class,
        QuestionAnswersDB::class,
        AnimalDB::class,
        RemoteKeyDB::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun questionAnswersDao(): QuestionAnswersDao
    abstract fun animalDao(): AnimalDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
