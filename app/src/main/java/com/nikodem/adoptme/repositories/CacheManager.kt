package com.nikodem.adoptme.repositories

interface CacheManager {
    fun <T : Cache> registerCache(cache: T): T
    fun invalidateAll()
}

class CacheManagerImpl : CacheManager {
    private val caches: MutableList<Cache> = mutableListOf()

    override fun <T : Cache> registerCache(cache: T): T {
        caches.add(cache)
        return cache
    }

    override fun invalidateAll() {
        caches.forEach { it.invalidate() }
    }
}