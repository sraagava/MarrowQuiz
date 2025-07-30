package com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.raagava.android.interview.apps.marrowquiz.presentation.app.Screens
import com.raagava.android.interview.apps.marrowquiz.presentation.components.ErrorView
import com.raagava.android.interview.apps.marrowquiz.presentation.components.ModuleCard
import com.raagava.android.interview.apps.marrowquiz.presentation.components.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ModuleListScreen(
    navController: NavController,
    viewModel: ModuleListViewModel = koinViewModel()
) {

    val quizModules = viewModel.moduleListState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TopBar(title = "Quiz Modules")
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {

            when (val currState = quizModules.value) {
                is ModuleListUiState.Error -> {
                    ErrorView(
                        title = "Something went wrong.",
                        description = currState.message,
                        actionButton = {
                            viewModel.getQuizModules()
                        }
                    )
                }

                ModuleListUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is ModuleListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(
                            currState.list.size,
                            key = { currState.list[it].id }
                        ) {
                            ModuleCard(
                                modifier = Modifier.fillMaxWidth(),
                                quizModule = currState.list[it],
                                onClick = { moduleId ->
                                    navController.navigate(
                                        Screens.QuizScreen(
                                            moduleId = moduleId,
                                            isReview = currState.list[it].pastAttempt != null
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}