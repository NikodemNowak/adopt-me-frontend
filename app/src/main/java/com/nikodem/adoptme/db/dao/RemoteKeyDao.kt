package com.nikodem.adoptme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.nikodem.adoptme.db.RemoteKeyDB

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeyDB>)

    @Query("DELETE FROM RemoteKeyDB")
    suspend fun deleteAll()

    @Query("SELECT * FROM RemoteKeyDB WHERE animalId = :animalId")
    suspend fun getByAnimalId(animalId: String): RemoteKeyDB?
}