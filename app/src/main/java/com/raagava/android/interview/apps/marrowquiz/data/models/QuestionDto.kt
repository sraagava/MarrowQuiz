package com.raagava.android.interview.apps.marrowquiz.data.models

import com.google.gson.annotations.SerializedName
import com.raagava.android.interview.apps.marrowquiz.domain.models.Question
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    @SerializedName("correctOptionIndex")
    val correctOptionIndex: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("options")
    val options: List<String>,
    @SerializedName("question")
    val question: String
)

fun QuestionDto.toDomain() = Question(
    correctOptionIndex = correctOptionIndex,
    id = id,
    options = options,
    question = question
)