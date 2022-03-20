package com.nikodem.adoptme.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.nikodem.adoptme.db.entity.AnimalDB

@Dao
interface AnimalDao {

    @Query("SELECT count(*) FROM AnimalDB")
    suspend fun count(): Int

    @Query("SELECT * FROM AnimalDB")
    suspend fun getAll(): List<AnimalDB>

    @Query("SELECT * FROM AnimalDB")
    fun pagingSource(): PagingSource<Int, AnimalDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animalDBS: List<AnimalDB>)

    @Delete
    suspend fun delete(animalDBS: AnimalDB)

    @Query("DELETE FROM AnimalDB")
    fun deleteAll()
}