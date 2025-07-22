package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states

import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizResult

sealed class ResultUiState {
    object Loading : ResultUiState()
    data class Success(val result: QuizResult) : ResultUiState()
}