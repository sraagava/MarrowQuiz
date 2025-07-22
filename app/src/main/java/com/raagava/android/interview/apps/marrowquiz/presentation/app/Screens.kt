package com.raagava.android.interview.apps.marrowquiz.presentation.app

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object SplashScreen : Screens()

    @Serializable
    data object QuestionsScreen : Screens()

    @Serializable
    data object ResultScreen : Screens()

}