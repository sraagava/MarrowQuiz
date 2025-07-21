package com.raagava.android.interview.apps.marrowquiz.data.local

import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto

class QuestionsCache {

    private val questions: MutableList<QuestionDto> = mutableListOf()

    fun setQuestions(newQuestions: List<QuestionDto>) {
        questions.clear()
        questions.addAll(newQuestions)
    }

    fun getQuestions(): List<QuestionDto> {
        return questions.toList()
    }
}