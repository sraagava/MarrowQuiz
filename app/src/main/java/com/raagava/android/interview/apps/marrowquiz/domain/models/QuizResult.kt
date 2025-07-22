package com.raagava.android.interview.apps.marrowquiz.domain.models

data class QuizResult(
    val total: Int = 0,
    val correct: Int = 0,
    val incorrect: Int = 0,
    val skipped: Int = 0,
    val highestStreak: Int = 0
)