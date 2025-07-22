package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import com.raagava.android.interview.apps.marrowquiz.domain.models.Question

//data class QuizUiState(
//    val currentQuestionIndex: Int = 5,
//    val questions: List<Question> = emptyList(),
//    val selectedOptions: Map<Int, Int> = emptyMap() // questionIndex -> selectedOptionIndex
//)

sealed class QuestionsUiState {
    object Loading : QuestionsUiState()
    data class Success(val questions: List<Question>) : QuestionsUiState()
    data class Error(val message: String) : QuestionsUiState()
}