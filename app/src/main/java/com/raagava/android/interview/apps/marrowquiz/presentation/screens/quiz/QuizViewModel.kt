package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.evaluate_answers.EvaluateAnswersUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.get_questions.GetQuestionsUseCase
import com.raagava.android.interview.apps.marrowquiz.domain.use_case.store_quiz_attempt.StoreQuizAttemptUseCase
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states.QuestionsUiState
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states.ResultUiState
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val evaluateAnswersUseCase: EvaluateAnswersUseCase,
    private val storeQuizAttemptUseCase: StoreQuizAttemptUseCase
) : ViewModel() {

    private var moduleId: String? = null

    private val _questionsState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val questionsState: StateFlow<QuestionsUiState> = _questionsState

    private val _currQuestionIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currQuestionIndex: StateFlow<Int> = _currQuestionIndex

    private val _userAnswers: MutableStateFlow<Map<Int, Int>> = MutableStateFlow(mapOf())
    val userAnswers: StateFlow<Map<Int, Int>> = _userAnswers

    private var maxQuestions: Int = 0

    private val _resultState: MutableStateFlow<ResultUiState?> = MutableStateFlow(null)
    val resultState: StateFlow<ResultUiState?> = _resultState

    private val _streakState: MutableStateFlow<Int> = MutableStateFlow(0)
    val streakState: StateFlow<Int> = _streakState

    var maxStreak: Int = 0
        private set

    init {
//        getQuestions()
    }

    fun registerAnswer(questionId: Int, optionIndex: Int) {
        val question = (questionsState.value as? QuestionsUiState.Success)
            ?.questions
            ?.find { it.id == questionId } ?: return

        _userAnswers.value = userAnswers.value.toMutableMap().apply {
            put(questionId, optionIndex)
        }

        //Update Streak
        val isCorrect = question.correctOptionIndex == optionIndex
        if (isCorrect) {
            _streakState.value += 1

            if (maxStreak < _streakState.value) {
                maxStreak = _streakState.value
            }
        } else {
            _streakState.value = 0
        }
    }

    fun updateCurrQuestionIndex(curr: Int) {
        if (curr < 0 || curr >= maxQuestions) return
        _currQuestionIndex.value = curr
    }

    fun getQuestions(moduleId: String) {
        viewModelScope.launch {
            getQuestionsUseCase(moduleId).collect { resp ->
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

    fun submitQuiz(moduleId: String) {
        viewModelScope.launch {
            val qState = questionsState.value
            val aState = userAnswers.value

            if (qState !is QuestionsUiState.Success) return@launch

            val result = evaluateAnswersUseCase.invoke(
                questions = qState.questions,
                answers = userAnswers.value
            )

            val uAnsList = mutableListOf<Int?>()
            for (i in 0 until qState.questions.size) {
                val q = qState.questions[i]
                val ans = aState[q.id]
                uAnsList.add(ans)
            }

            storeQuizAttemptUseCase.invoke(
                moduleId = moduleId,
                answers = uAnsList,
                total = result.total,
                correct = result.correct
            )

            _resultState.value = ResultUiState.Success(result)
        }
    }
}