package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserVerifyPin

interface VerifyPinUseCase {
    suspend fun invoke(verifyPin: String)
}

class VerifyPinUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : VerifyPinUseCase {
    override suspend fun invoke(verifyPin: String) {
        val tokens = adoptMeRepository.verifyPin(
            VerifyPin(
                verifyPin,
                tokenRepository.getSession()!!
            ).toNetwork()
        )
        tokenRepository.setTokens(tokens.accessToken, tokens.refreshToken)
    }
}

fun VerifyPin.toNetwork() = UserVerifyPin(pin, session)