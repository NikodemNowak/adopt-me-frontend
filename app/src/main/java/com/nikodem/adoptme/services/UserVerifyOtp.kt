package com.nikodem.adoptme.services

data class UserVerifyOtp(
    val otpCode: String,
    val session: String
)