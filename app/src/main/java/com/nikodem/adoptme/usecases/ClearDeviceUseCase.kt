package com.nikodem.adoptme.usecases

import com.nikodem.adoptme.repositories.CacheManager

interface ClearDeviceUseCase {
    fun invoke()
}

class ClearDeviceUseCaseImpl(
    private val cacheManager: CacheManager
): ClearDeviceUseCase {
    override fun invoke() {
        cacheManager.invalidateAll()
    }
}