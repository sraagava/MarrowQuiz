package com.raagava.android.interview.apps.marrowquiz.data.repository

import com.raagava.android.interview.apps.marrowquiz.data.local.QuestionsCache
import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto
import com.raagava.android.interview.apps.marrowquiz.data.remote.QuizApi
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val api: QuizApi,
    private val cache: QuestionsCache
) : QuizRepository {

    override suspend fun getQuizQuestions(moduleId: String): List<QuestionDto> {
        val questions = cache.getQuestions(moduleId)
        if (!questions.isNullOrEmpty()) {
            return questions
        }
        try {
            val url = cache.getModules().find { it.id == moduleId }?.questionsUrl
                ?: throw Exception("Questions URL not found")
            val resp = api.getQuestions(url)
            cache.setQuestions(moduleId, questions ?: listOf())
            return resp
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getQuizModules(): List<QuizModuleDto> {
        val questions = cache.getModules()
        if (questions.isNotEmpty()) {
            return questions
        }
        try {
            val resp = api.getQuizModules()
            cache.setModules(resp)
            return resp
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun storeUserQuizAttempt(
        moduleId: String,
        answers: List<Int?>,
        total: Int,
        correct: Int
    ) {
        cache.storeAttempt(
            moduleId,
            answers,
            total,
            correct
        )
    }

    override suspend fun getPastAttempts(): Map<String, PastModuleAttempt> {
        return cache.getPastAttempts()
    }

    override suspend fun getPastUserAnswers(moduleId: String): List<Int?>? {
        return cache.getQuizUserAnswers(moduleId)
    }
}