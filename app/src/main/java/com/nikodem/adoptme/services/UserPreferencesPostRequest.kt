package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPreferencesPostRequest(
    @Json(name = "user") val userId: String,
    @Json(name = "questionAnswers") val questionAnswersId: String,
    @Json(name = "answer") val answer: String,
)