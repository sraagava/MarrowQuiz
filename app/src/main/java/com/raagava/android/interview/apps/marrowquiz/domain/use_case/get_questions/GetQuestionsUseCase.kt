package com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions

import com.raagava.android.interview.apps.marrowquiz.data.models.toDomain
import com.raagava.android.interview.apps.marrowquiz.data.utils.safeApiCall
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.repository.QuizRepository
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuestionsUseCase(
    private val repository: QuizRepository
) {

    operator fun invoke(moduleId: String): Flow<DataResponse<List<Question>>> = flow {
        emit(DataResponse.Loading)
        val response = safeApiCall {
            repository.getQuizQuestions(moduleId).map { it.toDomain() }
        }
        if (response is DataResponse.Success) {

            val qList = mutableListOf<Question>()

            val previousAnswers = repository.getPastUserAnswers(moduleId)
            if (!previousAnswers.isNullOrEmpty()) {
                response.data.forEachIndexed { ind, item ->
                    qList.add(
                        item.copy(
                            userAnswerIndex = ind
                        )
                    )
                }
            } else {
                qList.addAll(response.data)
            }
            emit(DataResponse.Success(qList))
            return@flow
        }
        emit(response)
    }
}