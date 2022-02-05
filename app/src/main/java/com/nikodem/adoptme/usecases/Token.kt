package com.nikodem.adoptme.usecases

data class Token(
    val accessToken: String,
    val refreshToken: String
)