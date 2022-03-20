package com.nikodem.adoptme.ui.home_screen

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nikodem.adoptme.db.AppDatabase
import com.nikodem.adoptme.db.RemoteKeyDB
import com.nikodem.adoptme.db.entity.AnimalDB
import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.usecases.Animal
import java.io.InvalidObjectException
import java.lang.RuntimeException

@OptIn(ExperimentalPagingApi::class)
class AnimalMediator(
    private val database: AppDatabase,
    private val adoptMeRepository: AdoptMeRepository
) : RemoteMediator<Int, AnimalDB>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimalDB>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                //when it's the first time we're loading data
                //or when PagingDataAdapter.refresh() is called
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 0
                }
                //When we need to load data at the beginning of the currently loaded data set:
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: // The LoadType is PREPEND so some data was loaded before,
                        // so we should have been able to get remote keys
                        // If the remoteKeys are null, then we're an invalid state and we have a bug
                        throw InvalidObjectException("Remote key and the prevKey should not be null")
                    // If the previous key is null, then we can't request more data
                    val prevKey = remoteKeys.previousKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.previousKey
                }
                //When we need to load data at the end of the currently loaded data set:
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    if (remoteKeys?.nextKey == null) {
                        throw InvalidObjectException("Remote key should not be null for $loadType")
                    }
                    remoteKeys.nextKey
                }
            }

            val animals = adoptMeRepository.getAnimals(page, state.config.pageSize).map { it.toDB() }
            val endOfPaginationReached = animals.isEmpty()

            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().deleteAll()
                    database.animalDao().deleteAll()
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = animals.map {
                    RemoteKeyDB(
                        animalId = it.animalId,
                        previousKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeyDao().insertAll(keys)
                database.animalDao().insertAll(animals)
            }

//            return MediatorResult.Error(RuntimeException("psuja"))
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, AnimalDB>
    ): RemoteKeyDB? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.animalId?.let { animalId ->
                database.remoteKeyDao().getByAnimalId(animalId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimalDB>): RemoteKeyDB? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { animal ->
                // Get the remote keys of the first items retrieved
                database.remoteKeyDao().getByAnimalId(animal.animalId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimalDB>): RemoteKeyDB? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { animal ->
                // Get the remote keys of the last item retrieved
                database.remoteKeyDao().getByAnimalId(animal.animalId)
            }
    }
}

fun Animal.toDB() = AnimalDB(
    animalId = animalId,
    animalType = animalType,
    race = race,
    color = color,
    shelter = shelter,
    privateOwner = privateOwner,
    age = age,
    name = name,
    photo = photo,
    description = description
)