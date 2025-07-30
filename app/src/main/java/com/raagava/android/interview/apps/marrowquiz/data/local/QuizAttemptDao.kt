package com.raagava.android.interview.apps.marrowquiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizAttemptEntity

@Dao
interface QuizAttemptDao {

    @Query("SELECT * FROM quiz_attempts")
    suspend fun getAll(): List<QuizAttemptEntity>

    @Query("SELECT * FROM quiz_attempts WHERE module_id = :moduleId")
    suspend fun getQuizAttempt(moduleId: String): QuizAttemptEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg quizAttempt: QuizAttemptEntity)

    @Delete
    suspend fun delete(quizAttempt: QuizAttemptEntity)
}