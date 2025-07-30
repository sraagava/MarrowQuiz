package com.raagava.android.interview.apps.marrowquiz.data.local

import com.raagava.android.interview.apps.marrowquiz.data.models.PastModuleAttempt
import com.raagava.android.interview.apps.marrowquiz.data.models.QuestionDto

class QuestionsCache {

    private val questions: MutableList<QuestionDto> = mutableListOf()

    //    private val quizData: HashMap<String, ModuleCache> = HashMap<String, ModuleCache>()
    private val pastAttempts: HashMap<String, PastModuleAttempt> =
        HashMap<String, PastModuleAttempt>()

    fun setQuestions(newQuestions: List<QuestionDto>) {
        questions.clear()
        questions.addAll(newQuestions)
    }

    fun getQuestions(): List<QuestionDto> {
        return questions.toList()
    }

    fun storeAttempt(moduleId: String, attempt: PastModuleAttempt) {
        pastAttempts.put(moduleId, attempt)
    }

    fun getQuizData(): HashMap<String, PastModuleAttempt> {
        return pastAttempts
    }
}
