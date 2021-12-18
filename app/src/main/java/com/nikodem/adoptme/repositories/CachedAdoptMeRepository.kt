package com.nikodem.adoptme.repositories

import com.nikodem.adoptme.db.dao.UserDao
import com.nikodem.adoptme.services.QuestionAnswersResponse

interface CachedAdoptMeRepository {
    var questionAnswers: List<QuestionAnswersResponse>
    var currentQuestion: Int
}

class CachedAdoptMeRepositoryImpl(
    private val userDao: UserDao
) : CachedAdoptMeRepository {
    override var questionAnswers: List<QuestionAnswersResponse> = mutableListOf()
    override var currentQuestion: Int = 0
}