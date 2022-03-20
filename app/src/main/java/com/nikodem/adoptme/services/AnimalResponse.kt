package com.nikodem.adoptme.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class AnimalResponse(
    @Json(name = "animalId") val animalId: String,
    @Json(name = "animalType") val animalType: String,
    @Json(name = "race") val race: String,
    @Json(name = "color") val color: String,
    @Json(name = "shelter") val shelter: String?,
    @Json(name = "privateOwner") val privateOwner: String?,
    @Json(name = "age") val age: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "photo") val photo: String?,
    @Json(name = "description") val description: String?
)