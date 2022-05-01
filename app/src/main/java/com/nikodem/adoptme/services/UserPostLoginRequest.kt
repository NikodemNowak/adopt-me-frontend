package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPostLoginRequest(
    @Json(name = "phoneNumber") val phoneNumber: String
)