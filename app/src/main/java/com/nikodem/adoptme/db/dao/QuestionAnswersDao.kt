package com.nikodem.adoptme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nikodem.adoptme.db.entity.QuestionAnswersDB

@Dao
interface QuestionAnswersDao {

    @Query("SELECT count(*) FROM QuestionAnswersDB")
    suspend fun count(): Int

    @Query("SELECT * FROM QuestionAnswersDB")
    suspend fun getAll(): List<QuestionAnswersDB>

    @Insert
    suspend fun insertAll(questionAnswerDBS: List<QuestionAnswersDB>)

    @Delete
    suspend fun delete(questionAnswersDB: QuestionAnswersDB)
}