package com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states.QuestionsUiState
import com.raagava.android.interview.apps.marrowquiz.utils.DataError
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    private val _loadState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val loadState: StateFlow<QuestionsUiState> = _loadState

    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {
            getQuestionsUseCase().collect { resp ->
                when (resp) {
                    is DataResponse.Success -> {
                        _loadState.value = QuestionsUiState.Success(resp.data)
                    }

                    is DataResponse.Error -> {
                        when (resp.error) {
                            is DataError.HttpError -> {
                                _loadState.value =
                                    QuestionsUiState.Error("${resp.error.code}: ${resp.error.message}")
                            }

                            is DataError.RequestError -> {
                                _loadState.value = QuestionsUiState.Error(resp.error.message)
                            }
                        }
                    }

                    DataResponse.Loading -> {
                        _loadState.value = QuestionsUiState.Loading
                    }
                }
            }
        }
    }

}