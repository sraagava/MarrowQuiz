package com.raagava.android.interview.apps.marrowquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizAttemptEntity
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizUserAnswerEntity

@Database(entities = [QuizAttemptEntity::class, QuizUserAnswerEntity::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun attemptsDao(): QuizAttemptDao
    abstract fun answersDao(): QuizAnswersDao
}