package com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions

import com.raagava.android.interview.apps.marrowquiz.data.models.toDomain
import com.raagava.android.interview.apps.marrowquiz.data.utils.safeApiCall
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.flow

class GetQuestionsUseCase(
    private val repository: QuizRepository
) {

    operator fun invoke() = flow {
        emit(DataResponse.Loading)
        val response = safeApiCall {
            repository.getQuizQuestions().map { it.toDomain() }
        }
        emit(response)
    }
}