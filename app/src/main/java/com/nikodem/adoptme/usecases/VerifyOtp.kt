package com.nikodem.adoptme.usecases

data class VerifyOtp(
    val otpCode: String,
    val session: String
)