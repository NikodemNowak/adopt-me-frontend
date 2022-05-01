package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserVerifyPin(
    @Json(name = "pin") val pin: String,
    @Json(name = "session") val session: String
)