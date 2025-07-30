package com.raagava.android.interview.apps.marrowquiz.domain.repository

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto

interface QuizRepository {

    suspend fun getQuizQuestions(): List<QuestionDto>
    suspend fun getQuizModules(): List<QuizModuleDto>
    suspend fun storeAttempt(moduleId: String, attempt: PastModuleAttempt)
    suspend fun getPastAttempts(): Map<String, PastModuleAttempt>
}