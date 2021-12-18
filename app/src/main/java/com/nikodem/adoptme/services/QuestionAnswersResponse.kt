package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionAnswersResponse(
    @Json(name = "id") val id: String,
    @Json(name = "answer1") val answer1: String,
    @Json(name = "answer2") val answer2: String,
    @Json(name = "answer3") val answer3: String,
    @Json(name = "answer4") val answer4: String,
    @Json(name = "questionText") val questionText: String
)