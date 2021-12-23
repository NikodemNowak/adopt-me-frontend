package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.services.AdoptMeApiService
import com.nikodem.adoptme.services.QuestionAnswersResponse
import com.nikodem.adoptme.services.UserPreferencesPostRequest
import com.nikodem.adoptme.usecases.QuestionAnswers

interface AdoptMeRepository {
    suspend fun getQuestionAnswers(): List<QuestionAnswers>
    suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest)
}

class AdoptMeRepositoryImpl(
    private val adoptMeApiService: AdoptMeApiService
) : AdoptMeRepository {
    override suspend fun getQuestionAnswers(): List<QuestionAnswers> {
        return adoptMeApiService.getQuestionAnswers().map { it.toDomain() }
    }

    override suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest) {
        adoptMeApiService.addUserPreferences(userPreferencesPostRequest)
    }
}

fun QuestionAnswersResponse.toDomain() = QuestionAnswers(
    uuid = id,
    questionText = questionText,
    answer1 = answer1,
    answer2 = answer2,
    answer3 = answer3,
    answer4 = answer4,
)