package com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_quiz_modules

import com.raagava.android.interview.apps.marrowquiz.data.models.toDomain
import com.raagava.android.interview.apps.marrowquiz.data.utils.safeApiCall
import com.raagava.android.interview.apps.marrowquiz.domain.models.PastAttempt
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizModule
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuizModulesUseCase(
    private val repository: QuizRepository
) {

    operator fun invoke(): Flow<DataResponse<List<QuizModule>>> = flow {
        emit(DataResponse.Loading)
        val quizModules = safeApiCall {
            repository.getQuizModules().map { it.toDomain() }
        }

        if (quizModules is DataResponse.Success) {
            val qModules = mutableListOf<QuizModule>()
            val pastAttempts = repository.getPastAttempts()

            for (module in quizModules.data) {
                val attempt = pastAttempts[module.id]
                if (attempt != null) {
                    qModules.add(
                        module.copy(
                            pastAttempt = PastAttempt(
                                attempt.correct,
                                attempt.totalQuestion
                            )
                        )
                    )
                } else {
                    qModules.add(module)
                }
            }

            emit(DataResponse.Success(qModules))
            return@flow
        } else {
            emit(quizModules)
        }
    }
}