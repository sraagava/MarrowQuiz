package com.raagava.android.interview.apps.marrowquiz.presentation.app

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object SplashScreen : Screens()

    @Serializable
    data object QuizScreen : Screens()

    @Serializable
    data class ResultScreen(
        val total: Int,
        val correct: Int,
        val incorrect: Int,
        val skipped: Int,
        val highestStreak: Int
    ) : Screens()

}