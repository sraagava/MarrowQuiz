package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizResult
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.evaluate_answers.EvaluateAnswersUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val evaluateAnswersUseCase: EvaluateAnswersUseCase
) : ViewModel() {

    private val _questionsState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val questionsState: StateFlow<QuestionsUiState> = _questionsState

    private val _currQuestionIndex: MutableState<Int> = mutableIntStateOf(0)
    val currQuestionIndex: MutableState<Int> = _currQuestionIndex

    private val _userAnswers: MutableState<Map<Int, Int>> = mutableStateOf(mapOf())
    val userAnswers: MutableState<Map<Int, Int>> = _userAnswers

    private var maxQuestions: Int = 0

    private val _resultState: MutableStateFlow<QuizResult?> = MutableStateFlow(null)
    val resultState: StateFlow<QuizResult?> = _resultState

    init {
        getQuestions()
    }

    fun registerAnswer(questionId: Int, optionIndex: Int) {
        userAnswers.value = userAnswers.value.toMutableMap().apply {
            put(questionId, optionIndex)
        }
    }

    fun moveToNextQuestion() {
        //Checking for index out of bound
        if (currQuestionIndex.value < maxQuestions - 1) {
            viewModelScope.launch {
                delay(2000L)
                updateCurrQuestionIndex(currQuestionIndex.value + 1)
            }
        }
    }

    fun updateCurrQuestionIndex(curr: Int) {
        if (curr < 0 || curr >= maxQuestions) return
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
                        maxQuestions = resp.data.size
                        _questionsState.value = QuestionsUiState.Success(resp.data)
                    }
                }
            }
        }
    }

    fun evaluateAnswers() {
        val qState = questionsState.value

        if (qState !is QuestionsUiState.Success) return

        val result = evaluateAnswersUseCase.invoke(
            qState.questions,
            userAnswers.value
        )
        _resultState.value = result
        Log.e("Debug", result.toString())
    }
}