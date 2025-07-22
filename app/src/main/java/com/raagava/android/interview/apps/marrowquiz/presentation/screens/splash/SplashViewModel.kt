package com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.utils.DataError
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    private val _loadState = MutableStateFlow<Boolean>(false)
    val loadState: StateFlow<Boolean> = _loadState

    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {
            getQuestionsUseCase().collect { resp ->
                when (resp) {
                    is DataResponse.Success -> {
                        _loadState.value = true
                    }

                    is DataResponse.Error -> {
                        when (resp.error) {
                            is DataError.HttpError -> {
                                Log.e("Debug", "error ${resp.error.code}")
                            }

                            is DataError.RequestError -> {
                                Log.e("Debug", "error ${resp.error.message}")
                            }
                        }
                    }

                    DataResponse.Loading -> {
                        Log.e("Debug", "Loading")
                    }
                }
            }
        }
    }

}