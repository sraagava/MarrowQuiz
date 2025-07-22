package com.raagava.android.interview.apps.marrowquiz.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizResult
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.QuizScreen
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.quiz.ResultScreen
import com.raagava.android.interview.apps.marrowquiz.presentation.screens.splash.SplashScreen

@Composable
fun App(
    innerPadding: PaddingValues
) {

    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        NavHost(
            navController = navController,
            startDestination = Screens.SplashScreen,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<Screens.SplashScreen> {
                SplashScreen(navController = navController)
            }
            composable<Screens.QuizScreen> {
                QuizScreen(navController = navController)
            }
            composable<Screens.ResultScreen> {
                val args = it.toRoute<Screens.ResultScreen>()
                ResultScreen(
                    navController = navController, result = QuizResult(
                        total = args.total,
                        correct = args.correct,
                        incorrect = args.incorrect,
                        skipped = args.skipped,
                        highestStreak = args.highestStreak
                    )
                )
            }
        }
    }
}