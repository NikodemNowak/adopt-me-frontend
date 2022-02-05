package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.CacheManager

interface ResetRegistrationUseCase {
    suspend fun invoke()
}

class ResetRegistrationUseCaseImpl(
    private val adoptMeRepository: AdoptMeRepository,
    private val cacheManager: CacheManager
) : ResetRegistrationUseCase {
    override suspend fun invoke() {
        adoptMeRepository.resetRegistration()
        cacheManager.invalidateAll()
    }
}