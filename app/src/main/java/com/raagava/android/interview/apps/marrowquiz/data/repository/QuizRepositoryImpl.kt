package com.raagava.android.interview.apps.marrowquiz.data.repository

import com.raagava.android.interview.apps.marrowquiz.data.local.QuestionsCache
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.remote.QuizApi
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val api: QuizApi,
    private val cache: QuestionsCache
) : QuizRepository {

    override suspend fun getQuizQuestions(): List<QuestionDto> {
//        val questions = cache.getQuestions()
//        if (questions.isNotEmpty()) {
//            return questions
//        }
        try {
            val resp = api.getQuestions()
//            cache.setQuestions(resp)

            return resp
        } catch (e: Exception) {
            throw e
        }
    }
}