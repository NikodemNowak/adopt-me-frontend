package com.nikodem.adoptme.services

import com.nikodem.adoptme.utils.ApiResult
import retrofit2.http.*

interface AdoptMeApiService {
    @GET("questionAnswers")
    suspend fun getQuestionAnswers(): ApiResult<List<QuestionAnswersResponse>>

    @POST("userPreferences")
    suspend fun addUserPreferences(@Body userPreferencesPostRequest: UserPreferencesPostRequest): ApiResult<Unit>

    @POST("register/addUser")
    suspend fun addUser(@Body userPostRequest: UserPostRequest): ApiResult<SessionResponse>

    @POST("register/verifyOtp")
    suspend fun verifyOtp(@Body userVerifyOtp: UserVerifyOtp): ApiResult<SessionResponse>

    @POST("register/setPin")
    suspend fun setPin(@Body userSetPin: UserSetPin): ApiResult<TokenResponse>

    @DELETE("users/{session}")
    fun deleteUserBySession(@Path("session") session: String): ApiResult<Unit>

    @GET("register/nextOnboardingStep")
    fun getNextOnboardingStep(@Header("session") session: String): ApiResult<String>

    @GET("animals")
    suspend fun getAnimals(
        @Query("animalType") animalType: String? = null,
        @Query("page") page: Int
    ): ApiResult<List<AnimalResponse>>
}