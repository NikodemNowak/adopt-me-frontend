package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.services.AdoptMeApiService
import com.nikodem.adoptme.services.QuestionAnswersResponse
import com.nikodem.adoptme.services.UserPreferencesPostRequest

interface AdoptMeRepository {
    suspend fun getQuestionAnswers(): List<QuestionAnswersResponse>
    suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest)
}

class AdoptMeRepositoryImpl(
    private val adoptMeApiService: AdoptMeApiService
) : AdoptMeRepository {
    override suspend fun getQuestionAnswers(): List<QuestionAnswersResponse> {
        return adoptMeApiService.getQuestionAnswers()
    }

    override suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest) {
        adoptMeApiService.addUserPreferences(userPreferencesPostRequest)
    }
}