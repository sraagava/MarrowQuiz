package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raagava.android.interview.apps.marrowquiz.presentation.app.Screens
import com.raagava.android.interview.apps.marrowquiz.presentation.components.ErrorView
import com.raagava.android.interview.apps.marrowquiz.presentation.components.LoaderDialog
import com.raagava.android.interview.apps.marrowquiz.presentation.components.QuestionSection
import com.raagava.android.interview.apps.marrowquiz.presentation.components.Streak
import com.raagava.android.interview.apps.marrowquiz.presentation.components.TopBar
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states.QuestionsUiState
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.states.ResultUiState
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectColor
import com.raagava.android.interview.apps.marrowquiz.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuizScreen(
    navController: NavController, viewModel: QuizViewModel = koinViewModel()
) {

    val questionsState by viewModel.questionsState.collectAsState()
    val currIndex by viewModel.currQuestionIndex
    val userAnswers by viewModel.userAnswers
    val streak by viewModel.streakState.collectAsState()

    val resultState = viewModel.resultState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var isActionButtonEnabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            TopBar(title = "Quiz")
            Streak(
                modifier = Modifier.align(Alignment.CenterEnd),
                streak = streak
            )
        }

        when (val qState = questionsState) {
            is QuestionsUiState.Error -> {
                ErrorView(
                    title = "Couldn't load questions",
                    description = qState.message,
                    actionButton = {
                        Button(onClick = { viewModel.getQuestions() }) {
                            Text(text = "Retry")
                        }
                    }
                )
            }

            QuestionsUiState.Loading -> {
                CircularProgressIndicator()
            }

            is QuestionsUiState.Success -> {
                if (qState.questions.isEmpty()) {
                    ErrorView(
                        title = "Couldn't load questions",
                        description = "There are no questions available at the moment",
                        actionButton = {
                            Button(onClick = { viewModel.getQuestions() }) {
                                Text(text = "Retry")
                            }
                        }
                    )
                } else {
                    LinearProgressIndicator(
                        progress = { (currIndex.toFloat() / qState.questions.size).toFloat() },
                        modifier = Modifier.fillMaxWidth(),
                        color = CorrectColor,
                        trackColor = MaterialTheme.colorScheme.secondary
                    )
                    val pager = rememberPagerState { qState.questions.size }
                    LaunchedEffect(pager.currentPage) {
                        viewModel.currQuestionIndex.value = pager.currentPage
                    }
                    HorizontalPager(
                        state = pager,
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.Top
                    ) { index ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp)
                                .verticalScroll(state = rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Question ${index + 1} of ${qState.questions.size}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            val selectedOption =
                                userAnswers.getOrDefault(qState.questions[index].id, null)
                            QuestionSection(
                                question = qState.questions[index],
                                selectedOption = selectedOption,
                                onOptionSelected = {
                                    viewModel.registerAnswer(qState.questions[index].id, it)

                                    if (index + 1 >= qState.questions.size) {
                                        // Submit can be manually done by the user
                                    } else {
                                        coroutineScope.launch {
                                            isActionButtonEnabled.value = false
                                            delay(2000L)
                                            pager.animateScrollToPage(index + 1)
                                            isActionButtonEnabled.value = true
                                        }

                                    }
                                })
                        }
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        onClick = {
                            if (pager.currentPage + 1 >= qState.questions.size) {
                                //Submitting the quiz for evaluation
                                viewModel.submitQuiz()
//                                navController.navigate(Screens.ResultScreen)
                            } else {
                                coroutineScope.launch {
                                    pager.animateScrollToPage(pager.currentPage + 1)
                                }
                            }
                        },
                        enabled = isActionButtonEnabled.value
                    ) {
                        if (isActionButtonEnabled.value) {
                            val ctaText = when {
                                //Last question
                                (currIndex + 1 >= qState.questions.size) -> "Submit"

                                //If not answered
                                userAnswers.getOrDefault(
                                    qState.questions[currIndex].id,
                                    null
                                ) == null -> "Skip"

                                else -> "Next"
                            }
                            Text(text = ctaText)
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }

    when (resultState.value) {
        ResultUiState.Loading -> {

        }

        is ResultUiState.Success -> {}
        else -> {}
    }

    when (val res = resultState.value) {
        ResultUiState.Loading -> {
            LoaderDialog(
                text = "Evaluating answers..",
                isDisplayed = true
            )
        }

        is ResultUiState.Success -> {
            navController.navigate(
                Screens.ResultScreen(
                    total = res.result.total,
                    correct = res.result.correct,
                    incorrect = res.result.incorrect,
                    skipped = viewModel.maxStreak,
                    highestStreak = res.result.highestStreak
                )
            ) {
                popUpTo<Screens.QuizScreen> { inclusive = true }
            }
        }

        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionsScreenPreview() {
    QuizScreen(
        navController = NavController(LocalContext.current)
    )
}