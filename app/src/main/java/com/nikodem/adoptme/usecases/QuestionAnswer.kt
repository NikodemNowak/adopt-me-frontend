package com.nikodem.adoptme.usecases

data class QuestionAnswers(
    val uuid: String,
    val questionText: String?,
    val answer1: String?,
    val answer2: String?,
    val answer3: String?,
    val answer4: String?,
)