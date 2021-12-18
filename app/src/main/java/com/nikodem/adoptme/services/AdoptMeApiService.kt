package com.nikodem.adoptme.services

import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface AdoptMeApiService {
    @GET("questionAnswers")
    suspend fun getQuestionAnswers(): List<QuestionAnswersResponse>

    @POST("userPreferences")
    suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest)
}