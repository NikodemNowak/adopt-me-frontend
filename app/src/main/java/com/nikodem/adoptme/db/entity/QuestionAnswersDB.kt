package com.nikodem.adoptme.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionAnswersDB(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "question_text") val questionText: String?,
    @ColumnInfo(name = "answer1") val answer1: String?,
    @ColumnInfo(name = "answer2") val answer2: String?,
    @ColumnInfo(name = "answer3") val answer3: String?,
    @ColumnInfo(name = "answer4") val answer4: String?,
)