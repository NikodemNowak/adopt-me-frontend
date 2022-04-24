package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository

interface GetUserUseCase {
    suspend fun invoke(): User
}

class GetUserCaseImpl(
    private val tokenRepository: TokenRepository,
    private val adoptMeRepository: AdoptMeRepository
) : GetUserUseCase {
    override suspend fun invoke(): User {
        val accessToken = tokenRepository.getAccessToken()
        return adoptMeRepository.getUser(accessToken)
    }
}