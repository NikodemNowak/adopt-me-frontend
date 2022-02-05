package com.nikodem.adoptme.usecases

data class SetPin(
    val pin: String,
    val session: String
)