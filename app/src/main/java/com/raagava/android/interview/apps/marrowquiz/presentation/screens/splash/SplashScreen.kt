package com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raagava.android.interview.apps.marrowquiz.presentation.app.Screens
import com.raagava.android.interview.apps.marrowquiz.presentation.components.ErrorView
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.module_list.ModuleListUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel()
) {

    val loadState by viewModel.loadState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (loadState) {
            is ModuleListUiState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Screens.ModuleListScreen) {
                        popUpTo(Screens.SplashScreen) { inclusive = true }
                    }
                }
            }

            is ModuleListUiState.Error -> {
                ErrorView(
                    title = "Couldn't load questions",
                    description = (loadState as ModuleListUiState.Error).message
                ) {
                    Button(onClick = { viewModel.getQuestions() }) {
                        Text("Retry")
                    }
                }
            }

            ModuleListUiState.Loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Setting up your quiz battlefield…"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = NavController(LocalContext.current))
}