package com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_quiz_modules.GetQuizModulesUseCase
import com.raagava.android.interview.apps.marrowquiz.utils.DataError
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ModuleListViewModel(
    private val getQuizModulesUseCase: GetQuizModulesUseCase
) : ViewModel() {

    private val _moduleListState: MutableStateFlow<ModuleListUiState> =
        MutableStateFlow(ModuleListUiState.Loading)
    val moduleListState: StateFlow<ModuleListUiState> = _moduleListState


    init {
        getQuizModules()
    }

    fun getQuizModules() {
        viewModelScope.launch {
            getQuizModulesUseCase.invoke().collect {
                when (it) {
                    is DataResponse.Success -> {
                        _moduleListState.value = ModuleListUiState.Success(it.data)
                    }

                    DataResponse.Loading -> {
                        _moduleListState.value = ModuleListUiState.Loading
                    }

                    is DataResponse.Error -> {
                        when (it.error) {
                            is DataError.HttpError -> {
                                _moduleListState.value =
                                    ModuleListUiState.Error("${it.error.code}: ${it.error.message} An HTTP error occurred. Please retry.")
                            }

                            is DataError.RequestError -> {
                                _moduleListState.value =
                                    ModuleListUiState.Error("Could not reach our servers. Please check your network.")
                            }
                        }
                    }
                }
            }
        }
    }
}