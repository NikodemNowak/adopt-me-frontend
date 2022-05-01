package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserPostLoginRequest

interface SendOtpUseCase {
    suspend fun invoke(userLogin: UserLogin)
}

class SendOtpUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : SendOtpUseCase {
    override suspend fun invoke(userLogin: UserLogin) {
        val session = adoptMeRepository.sendOtp(userLogin.toNetwork())
        tokenRepository.setSession(session.session)
    }

}

fun UserLogin.toNetwork() = UserPostLoginRequest(phoneNumber)