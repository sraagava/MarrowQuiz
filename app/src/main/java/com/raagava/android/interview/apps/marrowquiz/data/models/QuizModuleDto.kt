package com.raagava.android.interview.apps.marrowquiz.data.models

import com.google.gson.annotations.SerializedName
import com.raagava.android.interview.apps.marrowquiz.domain.models.QuizModule

data class QuizModuleDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("questions_url")
    val questionsUrl: String
)

fun QuizModuleDto.toDomain() = QuizModule(
    id = id,
    title = title,
    description = description,
    pastAttempt = null
)