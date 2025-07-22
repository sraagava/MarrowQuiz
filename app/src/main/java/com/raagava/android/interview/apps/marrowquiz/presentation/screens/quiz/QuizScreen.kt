package com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raagava.android.interview.apps.marrowquiz.presentation.app.Screens
import com.raagava.android.interview.apps.marrowquiz.presentation.components.ErrorView
import com.raagava.android.interview.apps.marrowquiz.presentation.components.QuestionSection
import com.raagava.android.interview.apps.marrowquiz.presentation.components.TopBar
import com.raagava.android.interview.apps.marrowquiz.ui.theme.CorrectColor
import com.raagava.android.interview.apps.marrowquiz.ui.theme.PrimaryColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuizScreen(
    navController: NavController, viewModel: QuizViewModel = koinViewModel()
) {

    val questionsState by viewModel.questionsState.collectAsState()
    val currIndex by viewModel.currQuestionIndex
    val userAnswers by viewModel.userAnswers

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopBar(title = "Quiz")

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
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Question ${currIndex + 1} of ${qState.questions.size}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        val selectedOption =
                            userAnswers.getOrDefault(qState.questions[currIndex].id, null)
                        QuestionSection(
                            question = qState.questions[currIndex],
                            selectedOption = selectedOption,
                            onOptionSelected = {
                                viewModel.registerAnswer(qState.questions[currIndex].id, it)

                                if (currIndex + 1 >= qState.questions.size) {
                                    navController.navigate(Screens.ResultScreen)
                                } else {
                                    viewModel.moveToNextQuestion()
                                }
                            })
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        onClick = {
                            if (currIndex + 1 >= qState.questions.size) {
                                navController.navigate(Screens.ResultScreen)
                            } else {
                                viewModel.updateCurrQuestionIndex(currIndex + 1)
                            }
                        }
                    ) {
                        Text(text = "Next")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionsScreenPreview() {
    QuizScreen(
        navController = NavController(LocalContext.current)
    )
}