package com.nikodem.adoptme.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class AnimalDB(
    @PrimaryKey val animalId: String,
    @ColumnInfo(name = "animal_type") val animalType: String,
    @ColumnInfo(name = "race") val race: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "shelter") val shelter: String?,
    @ColumnInfo(name = "private_owner") val privateOwner: String?,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "photo") val photo: String?,
    @ColumnInfo(name = "description") val description: String?
)