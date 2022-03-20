package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.CachedAdoptMeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetAnimalsUseCase {
    suspend fun invoke(page: Int, pageSize: Int, onlyCached: Boolean = false): Flow<List<Animal>>
}

class GetAnimalsUseCaseImpl(
    private val cachedAdoptMeRepository: CachedAdoptMeRepository,
    private val adoptMeRepository: AdoptMeRepository
) : GetAnimalsUseCase {
    override suspend fun invoke(page: Int, pageSize: Int, onlyCached: Boolean): Flow<List<Animal>> = flow {
        if (cachedAdoptMeRepository.isCached()) {
            emit(cachedAdoptMeRepository.getCachedAnimals())
        }

        if (!onlyCached) {
            val a = adoptMeRepository.getAnimals(page = page, pageSize = pageSize)
            emit(a)
            cachedAdoptMeRepository.saveAnimals(a)
        }
    }
}