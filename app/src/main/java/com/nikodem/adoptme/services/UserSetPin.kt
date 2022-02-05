package com.nikodem.adoptme.services

data class UserSetPin(
    val pin: String,
    val session: String
)