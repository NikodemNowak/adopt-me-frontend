package com.nikodem.adoptme.usecases

data class UserPreference(
    val userId: String,
    val questionAnswerId: String,
    val answer: String
)