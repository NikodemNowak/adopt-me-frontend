package com.nikodem.adoptme.services

data class UserResponse(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val city: String
)