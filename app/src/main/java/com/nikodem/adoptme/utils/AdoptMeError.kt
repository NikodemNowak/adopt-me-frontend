package com.nikodem.adoptme.utils

import java.lang.RuntimeException

abstract class AdoptMeException : RuntimeException() {
    abstract val originalException: Throwable?
}

data class ApiException(
    override val message: String,
    val errorCode: Int,
    override val originalException: Throwable? = null
) : AdoptMeException()

sealed class AdoptMeError : AdoptMeException() {
    data class UnknownError(override val originalException: Throwable? = null) : AdoptMeError()
    data class NetworkError(override val originalException: Throwable? = null) : AdoptMeError()
    data class OtherError(override val originalException: Throwable? = null) : AdoptMeError()

    data class EmailTakenError(override val originalException: Throwable? = null) : AdoptMeError()
    data class PhoneTakenError(override val originalException: Throwable? = null) : AdoptMeError()

    companion object {
        const val EMAIL_TAKEN_ERROR_CODE = 1234
        const val PHONE_TAKEN_ERROR_CODE = 9182
    }
}