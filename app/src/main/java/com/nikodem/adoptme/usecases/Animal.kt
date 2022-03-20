package com.nikodem.adoptme.usecases

import java.util.*

data class Animal(
    val animalId: String,
    val animalType: String,
    val race: String,
    val color: String,
    val shelter: String?,
    val privateOwner: String?,
    val age: Int,
    val name: String?,
    val photo: String?,
    val description: String?
)