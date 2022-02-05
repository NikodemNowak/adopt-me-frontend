package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.NextOnboardingStep
import com.nikodem.adoptme.services.*
import com.nikodem.adoptme.usecases.QuestionAnswers
import com.nikodem.adoptme.usecases.Session
import com.nikodem.adoptme.usecases.Token
import com.nikodem.adoptme.utils.NetworkHandler

interface AdoptMeRepository {
    suspend fun getQuestionAnswers(): List<QuestionAnswers>
    suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest)
    suspend fun addUser(userPostRequest: UserPostRequest): Session
    suspend fun verifyOtp(userVerifyOtp: UserVerifyOtp): Session
    suspend fun setPin(userSetPin: UserSetPin): Token
    suspend fun getNextOnboardingStep(): NextOnboardingStep
    suspend fun resetRegistration()
}

class AdoptMeRepositoryImpl(
    private val adoptMeApiService: AdoptMeApiService,
    private val networkHandler: NetworkHandler,
    private val tokenRepository: TokenRepository
) : AdoptMeRepository {
    override suspend fun getQuestionAnswers(): List<QuestionAnswers> {
        return networkHandler.safeNetworkCall {
            adoptMeApiService.getQuestionAnswers()
        }.map { it.toDomain() }
    }

    override suspend fun addUserPreferences(userPreferencesPostRequest: UserPreferencesPostRequest) {
        networkHandler.safeNetworkCall {
            adoptMeApiService.addUserPreferences(userPreferencesPostRequest)
        }
    }

    override suspend fun addUser(userPostRequest: UserPostRequest): Session {
        return networkHandler.safeNetworkCall {
            adoptMeApiService.addUser(userPostRequest)
        }.toDomain()
    }

    override suspend fun verifyOtp(userVerifyOtp: UserVerifyOtp): Session {
        return networkHandler.safeNetworkCall {
            adoptMeApiService.verifyOtp(userVerifyOtp)
        }.toDomain()
    }

    override suspend fun setPin(userSetPin: UserSetPin): Token {
        return networkHandler.safeNetworkCall {
            adoptMeApiService.setPin(userSetPin)
        }.toDomain()
    }

    override suspend fun getNextOnboardingStep(): NextOnboardingStep {
        val step = networkHandler.safeNetworkCall {
            adoptMeApiService.getNextOnboardingStep(tokenRepository.getSession()!!)
        }
        return NextOnboardingStep.valueOf(step)
    }

    override suspend fun resetRegistration() {
        networkHandler.safeNetworkCall {
            adoptMeApiService.deleteUserBySession(tokenRepository.getSession()!!)
        }
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

fun SessionResponse.toDomain() = Session(session)

fun TokenResponse.toDomain() = Token(accessToken, refreshToken)