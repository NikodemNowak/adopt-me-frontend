package com.nikodem.adoptme.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class NetworkHandler {
    @Suppress("ThrowsCount", "TooGenericExceptionCaught")
    suspend fun <T> safeNetworkCall(
        errorMapper: (Throwable) -> Throwable = { it },
        block: suspend CoroutineScope.() -> ApiResult<T>
    ): T =
        coroutineScope {
            try {
                when (val apiResult: ApiResult<T> = block()) {
                    is ApiResult.Success -> apiResult.data!!
                    is ApiResult.Failure -> {
                        if (apiResult.apiError != null) {
                            throw apiResult.apiError.toApiException().toAdoptMeError()
                        } else {
                            throw AdoptMeError.UnknownError()
                        }
                    }
                    is ApiResult.NetworkError -> throw AdoptMeError.NetworkError()
                    is ApiResult.OtherError -> throw AdoptMeError.OtherError()
                }
            } catch (throwable: Throwable) {
                throw errorMapper(throwable)
            }
        }
}

private fun ApiErrorResponse.toApiException() = ApiException(
    message = message,
    errorCode = errorCode
)

private fun ApiException.toAdoptMeError(): AdoptMeError {
    return when (errorCode) {
        AdoptMeError.EMAIL_TAKEN_ERROR_CODE -> {
            AdoptMeError.EmailTakenError(this)
        }
        AdoptMeError.PHONE_TAKEN_ERROR_CODE -> {
            AdoptMeError.PhoneTakenError(this)
        }
        else -> {
            AdoptMeError.UnknownError(this)
        }
    }
}