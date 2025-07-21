package com.raagava.android.interview.apps.marrowquiz.domain.repository

import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto

interface QuizRepository {

    suspend fun getQuizQuestions(): List<QuestionDto>
}