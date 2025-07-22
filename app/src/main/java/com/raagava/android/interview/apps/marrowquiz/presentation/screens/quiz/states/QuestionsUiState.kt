package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states

import com.raagava.android.interview.apps.marrowquiz.domain.models.Question

sealed class QuestionsUiState {
    object Loading : QuestionsUiState()
    data class Success(val questions: List<Question>) : QuestionsUiState()
    data class Error(val message: String) : QuestionsUiState()
}