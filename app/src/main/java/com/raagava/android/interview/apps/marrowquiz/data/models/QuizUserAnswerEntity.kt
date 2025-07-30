package com.raagava.android.interview.apps.marrowquiz.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "quiz_user_answers", primaryKeys = ["module_id", "q_id"])
data class QuizUserAnswerEntity(
    @ColumnInfo("module_id")
    val moduleId: String,
    @ColumnInfo("q_id")
    val questionId: Int,
    @ColumnInfo("ans_idx")
    val answerIndex: Int,
)