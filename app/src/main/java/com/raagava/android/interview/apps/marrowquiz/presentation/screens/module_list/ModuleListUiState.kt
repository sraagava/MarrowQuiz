package com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list

import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizModule

sealed class ModuleListUiState {
    object Loading : ModuleListUiState()
    data class Success(val list: List<QuizModule>) : ModuleListUiState()
    data class Error(val message: String) : ModuleListUiState()
}