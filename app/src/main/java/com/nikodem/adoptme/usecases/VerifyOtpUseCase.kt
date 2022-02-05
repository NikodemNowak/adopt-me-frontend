package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserVerifyOtp

interface VerifyOtpUseCase {
    suspend fun invoke(verifyOtp: String)
}

class VerifyOtpUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : VerifyOtpUseCase {
    override suspend fun invoke(verifyOtp: String) {
        if (!tokenRepository.getSession().isNullOrEmpty()) {
            val session = adoptMeRepository.verifyOtp(
                VerifyOtp(
                    verifyOtp,
                    tokenRepository.getSession()!!
                ).toNetwork()
            )
            tokenRepository.setSession(session = session.session)
        }

    }

}

fun VerifyOtp.toNetwork() = UserVerifyOtp(otpCode, session)