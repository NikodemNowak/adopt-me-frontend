package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserSetPin

interface SetPinUseCase {
    suspend fun invoke(setPin: String)
}

class SetPinUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : SetPinUseCase {
    override suspend fun invoke(setPin: String) {
        if (!tokenRepository.getSession().isNullOrEmpty()) {
            val tokens = adoptMeRepository.setPin(
                SetPin(
                    pin = setPin,
                    session = tokenRepository.getSession()!!
                ).toNetwork()
            )
            tokenRepository.setTokens(
                accessToken = tokens.accessToken,
                refreshToken = tokens.refreshToken
            )
        }
    }

}

fun SetPin.toNetwork() = UserSetPin(pin, session)