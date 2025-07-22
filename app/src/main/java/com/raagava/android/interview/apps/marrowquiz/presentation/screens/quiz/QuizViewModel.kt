package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : ViewModel() {

    private val _questionsState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val questionsState: StateFlow<QuestionsUiState> = _questionsState

    private val _currQuestionIndex: MutableState<Int> = mutableIntStateOf(0)
    val currQuestionIndex: MutableState<Int> = _currQuestionIndex

    private val _userAnswers: MutableState<Map<Int, Int>> = mutableStateOf(mapOf())
    val userAnswers: MutableState<Map<Int, Int>> = _userAnswers

    init {
        getQuestions()
    }

    fun registerAnswer(questionIndex: Int, optionIndex: Int) {
        userAnswers.value = userAnswers.value.toMutableMap().apply {
            put(questionIndex, optionIndex)
        }
    }

    fun moveToNextQuestion() {
        viewModelScope.launch {
            delay(2000L)
            updateCurrQuestionIndex(currQuestionIndex.value + 1)
        }
    }

    fun updateCurrQuestionIndex(curr: Int) {
        currQuestionIndex.value = curr
    }

    fun getQuestions() {
        viewModelScope.launch {
            getQuestionsUseCase().collect { resp ->
                when (resp) {
                    DataResponse.Loading -> {
                        _questionsState.value = QuestionsUiState.Loading
                    }

                    is DataResponse.Error -> {
                        _questionsState.value = QuestionsUiState.Error(resp.error.toString())
                    }

                    is DataResponse.Success<List<Question>> -> {
                        _questionsState.value = QuestionsUiState.Success(resp.data)
                    }
                }
            }
        }
    }
}