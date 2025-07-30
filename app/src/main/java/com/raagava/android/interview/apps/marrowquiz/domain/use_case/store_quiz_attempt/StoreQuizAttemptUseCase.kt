package com.raagava.android.interview.apps.marrowquiz.domain.use_case.store_quiz_attempt

import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository

class StoreQuizAttemptUseCase(
    private val repository: QuizRepository
) {

    suspend operator fun invoke(
        moduleId: String,
        questions: List<Question>,
        total: Int,
        correct: Int
    ) {
        repository.storeUserQuizAttempt(
            moduleId,
            questions,
            total,
            correct
        )
    }
}