package com.raagava.android.interview.apps.marrowquiz.domain.repository

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question

interface QuizRepository {

    suspend fun getQuizQuestions(moduleId: String): List<QuestionDto>
    suspend fun getQuizModules(): List<QuizModuleDto>

    suspend fun storeUserQuizAttempt(
        moduleId: String,
        questions: List<Question>,
        total: Int,
        correct: Int
    )

    suspend fun getPastAttempts(): Map<String, PastModuleAttempt>
    suspend fun getPastUserAnswers(moduleId: String): Map<Int, Int?>
}