package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.TokenRepository

interface IsLoggedInUseCase {
    fun invoke(): Boolean
}

class IsLoggedInUseCaseImpl(
    private val tokenRepository: TokenRepository
) : IsLoggedInUseCase {
    override fun invoke() = runCatching {
        tokenRepository.getAccessToken()
        tokenRepository.getRefreshToken()
    }.isSuccess
}
