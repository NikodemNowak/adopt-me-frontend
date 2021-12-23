package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.services.UserPreferencesPostRequest

interface AddUserPreferenceUseCase {
    suspend fun invoke(userPreference: UserPreference)
}

class AddUserPreferenceUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository
) : AddUserPreferenceUseCase{
    override suspend fun invoke(userPreference: UserPreference) {
        adoptMeRepository.addUserPreferences(userPreference.toNetwork())
    }
}

fun UserPreference.toNetwork() = UserPreferencesPostRequest(
    userId = userId,
    questionAnswersId = questionAnswerId,
    answer = answer
)