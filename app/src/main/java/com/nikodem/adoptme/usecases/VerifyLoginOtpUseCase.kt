package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserVerifyOtp

interface VerifyLoginOtpUseCase {
    suspend fun invoke(verifyOtp: String)
}

class VerifyLoginOtpUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : VerifyLoginOtpUseCase {
    override suspend fun invoke(verifyOtp: String) {
        if (!tokenRepository.getSession().isNullOrEmpty()) {
            val session =
                adoptMeRepository.verifyLoginOtp(
                    VerifyOtp(
                        verifyOtp,
                        tokenRepository.getSession()!!
                    ).toNetwork1()
                )
            tokenRepository.setSession(session.session)
        }
    }

}

fun VerifyOtp.toNetwork1() = UserVerifyOtp(otpCode, session)