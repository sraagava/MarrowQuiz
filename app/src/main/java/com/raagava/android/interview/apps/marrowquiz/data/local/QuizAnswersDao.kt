package com.raagava.android.interview.apps.marrowquiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizAttemptEntity
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizUserAnswerEntity

@Dao
interface QuizAnswersDao {

    @Query("SELECT * FROM quiz_user_answers")
    suspend fun getAll(): List<QuizUserAnswerEntity>

    @Query("SELECT * FROM quiz_user_answers WHERE module_id = :moduleId")
    suspend fun getAnswers(moduleId: String): List<QuizUserAnswerEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg quizAnswer: QuizUserAnswerEntity)

    @Delete
    suspend fun delete(quizAttempt: QuizAttemptEntity)

}