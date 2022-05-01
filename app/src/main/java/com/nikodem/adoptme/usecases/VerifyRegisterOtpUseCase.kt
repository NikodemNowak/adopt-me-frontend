package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserVerifyOtp

interface VerifyRegisterOtpUseCase {
    suspend fun invoke(verifyOtp: String)
}

class VerifyRegisterOtpUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : VerifyRegisterOtpUseCase {
    override suspend fun invoke(verifyOtp: String) {
        if (!tokenRepository.getSession().isNullOrEmpty()) {
            val session = adoptMeRepository.verifyRegisterOtp(
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