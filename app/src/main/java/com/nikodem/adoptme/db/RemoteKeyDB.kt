package com.nikodem.adoptme.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyDB(
    @PrimaryKey val animalId: String,
    val previousKey: Int?,
    val nextKey: Int?,
    )