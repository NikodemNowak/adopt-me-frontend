package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.db.dao.AnimalDao
import com.nikodem.adoptme.db.dao.QuestionAnswersDao
import com.nikodem.adoptme.db.dao.UserDao
import com.nikodem.adoptme.db.entity.AnimalDB
import com.nikodem.adoptme.db.entity.QuestionAnswersDB
import com.nikodem.adoptme.usecases.Animal
import com.nikodem.adoptme.usecases.QuestionAnswers

interface CachedAdoptMeRepository {
    suspend fun isCached(): Boolean
    suspend fun saveQuestionAnswers(list: List<QuestionAnswers>)
    suspend fun getCachedQuestionAnswers(): List<QuestionAnswers>
    suspend fun saveAnimals(list: List<Animal>)
    suspend fun getCachedAnimals(): List<Animal>
}

class CachedAdoptMeRepositoryImpl(
    private val userDao: UserDao,
    private val questionAnswersDao: QuestionAnswersDao,
    private val animalDao: AnimalDao
) : CachedAdoptMeRepository, Cache {
    override suspend fun isCached(): Boolean {
        return questionAnswersDao.count() > 0
    }

    override suspend fun saveQuestionAnswers(list: List<QuestionAnswers>) {
        questionAnswersDao.insertAll(list.toEntity())
    }

    override suspend fun getCachedQuestionAnswers(): List<QuestionAnswers> {
        return questionAnswersDao.getAll().map { it.toDomain() }
    }

    override suspend fun saveAnimals(list: List<Animal>) {
        animalDao.insertAll(list.toEntity())
    }

    override suspend fun getCachedAnimals(): List<Animal> {
        return animalDao.getAll().map { it.toDomain() }
    }

    override fun invalidate() {
        userDao.deleteAll()
        questionAnswersDao.deleteAll()
    }
}

fun QuestionAnswersDB.toDomain() = QuestionAnswers(
    uuid = uid,
    questionText = questionText,
    answer1 = answer1,
    answer2 = answer2,
    answer3 = answer3,
    answer4 = answer4,
)

fun AnimalDB.toDomain() = Animal(
    animalId, animalType, race, color, shelter, privateOwner, age, name, photo, description
)

fun QuestionAnswers.toEntity() = QuestionAnswersDB(
    uuid, questionText, answer1, answer2, answer3, answer4
)

fun Animal.toEntity() = AnimalDB(
    animalId, animalType, race, color, shelter, privateOwner, age, name, photo, description
)

fun List<QuestionAnswers>.toEntity() = map {
    it.toEntity()
}

@JvmName("toEntityAnimal")
fun List<Animal>.toEntity() = map {
    it.toEntity()
}