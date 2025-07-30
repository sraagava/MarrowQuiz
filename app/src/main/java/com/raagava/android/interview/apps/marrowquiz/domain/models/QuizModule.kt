package com.raagava.android.interview.apps.marrowquiz.domain.models

data class QuizModule(
    val id: String,
    val title: String,
    val description: String,
    val pastAttempt: PastAttempt?
)

data class PastAttempt(
    val score: Int,
    val totalQuestions: Int
)