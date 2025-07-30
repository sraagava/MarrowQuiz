package com.raagava.android.interview.apps.marrowquiz.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "quiz_attempts", primaryKeys = ["module_id"])
data class QuizAttemptEntity(
    @ColumnInfo(name = "module_id")
    val moduleId: String,
    @ColumnInfo(name = "total_question")
    val totalQuestion: Int,
    @ColumnInfo(name = "correct")
    val correct: Int
)