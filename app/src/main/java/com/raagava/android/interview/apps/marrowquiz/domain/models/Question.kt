package com.raagava.android.interview.apps.marrowquiz.domain.models

data class Question(
    val correctOptionIndex: Int,
    val id: Int,
    val options: List<String>,
    val question: String
)