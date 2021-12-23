package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.CachedAdoptMeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetQuestionAnswersUseCase {
    suspend fun invoke(onlyCached: Boolean = false): Flow<List<QuestionAnswers>>
}

class GetQuestionAnswersUseCaseImpl(
    private val cachedAdoptMeRepository: CachedAdoptMeRepository,
    private val adoptMeRepository: AdoptMeRepository
) : GetQuestionAnswersUseCase {
    override suspend fun invoke(onlyCached: Boolean): Flow<List<QuestionAnswers>> = flow {
        if (cachedAdoptMeRepository.isCached()) {
            emit(cachedAdoptMeRepository.getCachedQuestionAnswers())
        }

        if (!onlyCached) {
            emit(adoptMeRepository.getQuestionAnswers())
        }
    }
}