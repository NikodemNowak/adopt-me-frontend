package com.nikodem.adoptme.services

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)