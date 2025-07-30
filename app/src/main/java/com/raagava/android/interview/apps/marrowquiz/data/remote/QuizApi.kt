package com.raagava.android.interview.apps.marrowquiz.data.remote

import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto
import com.raagava.android.interview.apps.marrowquiz.data.models.QuizModuleDto
import retrofit2.http.GET
import retrofit2.http.Url

interface QuizApi {

    @GET
    suspend fun getQuestions(@Url url: String): List<QuestionDto>

    @GET("/dr-samrat/ee986f16da9d8303c1acfd364ece22c5/raw")
    suspend fun getQuizModules(): List<QuizModuleDto>
}