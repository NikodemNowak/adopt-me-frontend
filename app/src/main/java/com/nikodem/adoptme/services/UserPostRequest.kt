package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPostRequest(
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "city") val city: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "email") val email: String,
)