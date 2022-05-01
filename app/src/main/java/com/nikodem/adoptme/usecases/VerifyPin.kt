package com.nikodem.adoptme.usecases

data class VerifyPin(
    val pin: String,
    val session: String
)