package com.raagava.android.interview.apps.marrowquiz.domain.repository

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto

interface QuizRepository {

    suspend fun getQuizQuestions(moduleId: String): List<QuestionDto>
    suspend fun getQuizModules(): List<QuizModuleDto>

    suspend fun storeUserQuizAttempt(
        moduleId: String,
        answers: List<Int?>,
        total: Int,
        correct: Int
    )

    suspend fun getPastAttempts(): Map<String, PastModuleAttempt>
    suspend fun getPastUserAnswers(moduleId: String): List<Int?>?
}