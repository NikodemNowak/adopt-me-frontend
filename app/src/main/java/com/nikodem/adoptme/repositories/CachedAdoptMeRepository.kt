package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.db.dao.QuestionAnswersDao
import com.nikodem.adoptme.db.dao.UserDao
import com.nikodem.adoptme.db.entity.QuestionAnswersDB
import com.nikodem.adoptme.usecases.QuestionAnswers

interface CachedAdoptMeRepository {
    suspend fun isCached(): Boolean
    suspend fun saveQuestionAnswers(list: List<QuestionAnswers>)
    suspend fun getCachedQuestionAnswers(): List<QuestionAnswers>
}

class CachedAdoptMeRepositoryImpl(
    private val userDao: UserDao,
    private val questionAnswersDao: QuestionAnswersDao
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

fun QuestionAnswers.toEntity() = QuestionAnswersDB(
    uid = uuid,
    questionText, answer1, answer2, answer3, answer4
)

fun List<QuestionAnswers>.toEntity() = map {
    it.toEntity()
}