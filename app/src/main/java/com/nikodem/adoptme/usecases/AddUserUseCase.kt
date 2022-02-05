package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.TokenRepository
import com.nikodem.adoptme.services.UserPostRequest

interface AddUserUseCase {
    suspend fun invoke(user: User)
}

class AddUserUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val tokenRepository: TokenRepository
) : AddUserUseCase {
    override suspend fun invoke(user: User) {
        val session = adoptMeRepository.addUser(user.toNetwork())
        tokenRepository.setSession(session = session.session)
    }
}

fun User.toNetwork() = UserPostRequest(
    firstName, lastName, city, phoneNumber, email
)