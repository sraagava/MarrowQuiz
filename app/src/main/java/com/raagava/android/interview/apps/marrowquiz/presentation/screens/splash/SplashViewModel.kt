package com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_quiz_modules.GetQuizModulesUseCase
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list.ModuleListUiState
import com.raagava.android.interview.apps.marrowquiz.utils.DataError
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getQuizModulesUseCase: GetQuizModulesUseCase
) : ViewModel() {

    private val _loadState = MutableStateFlow<ModuleListUiState>(ModuleListUiState.Loading)
    val loadState: StateFlow<ModuleListUiState> = _loadState

    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {
            getQuizModulesUseCase().collect { resp ->
                when (resp) {
                    is DataResponse.Success -> {
                        _loadState.value = ModuleListUiState.Success(resp.data)
                    }

                    is DataResponse.Error -> {
                        when (resp.error) {
                            is DataError.HttpError -> {
                                _loadState.value =
                                    ModuleListUiState.Error("${resp.error.code}: ${resp.error.message}")
                            }

                            is DataError.RequestError -> {
                                _loadState.value = ModuleListUiState.Error(resp.error.message)
                            }
                        }
                    }

                    DataResponse.Loading -> {
                        _loadState.value = ModuleListUiState.Loading
                    }
                }
            }
        }
    }

}